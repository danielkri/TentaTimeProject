# -*- coding: utf-8 -*-
from flask import Flask, request, jsonify
from htmlparser.SpExamParser import parse
from flask.ext.cache import Cache

#TODO: maybe introduce earlier validation to give error to the user as quick as possible
#Setup cache
cache = Cache(config={'CACHE_TYPE': 'memcached'})
app = Flask(__name__)
cache.init_app(app)


@app.route('/exams', methods=['GET'])
def exams():
	json_result = []
	if request.method == 'GET':
		try:
			json_result = parser(code=request.args.get('course_code'),name=request.args.get('course_name'),offset=request.args.get('offset'),limit=request.args.get('limit'))
		except Exception, err:
			print Exception, err
			return not_found()
		resp = jsonify(exams=json_result)
		resp.status_code = 200
		return resp
@app.route('/exams/<string:course_code>', methods=['GET'])
def exam(course_code):
	if request.method == 'GET':
			try:
				json_result = parser(code=course_code)
			except Exception, err:
				print Exception, err
				return not_found()
			return jsonify(exams=json_result)

"Caching the function arguments using memoize"
#@cache.cached(timeout=8000, key_prefix='json_result')
@cache.memoize(timeout=86400)
def parser(**kwargs):
	return parse(**kwargs)


@app.errorhandler(404)
def not_found(error=None):
    message = {
            'status': 404,
            'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404
    return resp

if __name__ == '__main__':
	app.run(debug=True,port=5003)
	