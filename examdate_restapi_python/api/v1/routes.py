# -*- coding: utf-8 -*-
from flask import Flask, request, jsonify
from htmlparser.SpExamParser import parse

#TODO: maybe introduce earlier validation to give error to the user as quick as possible

app = Flask(__name__)


@app.route('/exams', methods=['GET'])
def exams():
	json_result = []
	if request.method == 'GET':
		try:
			json_result = parse(code=request.args.get('course_code'),name=request.args.get('course_name'),offset=request.args.get('offset'),limit=request.args.get('limit'))
		except Exception:
			return not_found()
		resp = jsonify(exams=json_result)
		resp.status_code = 200
		return resp
@app.route('/exams/<string:course_code>', methods=['GET'])
def exam(course_code):
	if request.method == 'GET':
			try:
				json_result = parse(code=course_code)
			except Exception:
				return not_found()
			return jsonify(exams=json_result)

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
	app.run(debug=True,port=5002)
	