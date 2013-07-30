# -*- coding: utf-8 -*-
from flask import Flask, request, jsonify
from htmlparser.SpExamParser import parseFast, validateCoursecode

app = Flask(__name__)
#default request url
default_url ='https://www.student.chalmers.se/sp/examdates_list?flag=1&sortorder=C_CODE,CM_CODE,EX_DATE'

@app.route('/exams', methods=['GET'])
def exams():
	json_result = []
	if request.method == 'GET':
		code = request.args.get('course_code')
		name = request.args.get('course_name')
		#year = request.args.get('ac_year') not compatible w studieportalen yet
	

		if name is None and code is not None:
				try:
					json_result = parseFast(default_url +'&search_course_code='+code)
				except Exception:
					return not_found()
		elif code is None and name is not None:
				try:	
					json_result = parseFast(default_url +'&search_course_name='+name)
				except Exception:
					return not_found()	
		elif code is not None and name is not None:
				try:
					json_result = parseFast(default_url +'&search_course_code='+code+'&search_course_name='+name)
				except Exception:
					return not_found()
		else:
				try:
					json_result = parseFast(default_url) #Gets all exams
				except Exception:
					return not_found()
		resp = jsonify(exams=json_result)
		resp.status_code = 200
		return resp
@app.route('/exams/<string:course_code>', methods=['GET'])
def exam(course_code):
	if request.method == 'GET':
		if validateCoursecode(course_code) is True:	
			try:
				json_result = parseFast(default_url +'&search_course_code='+course_code)
			except Exception:
				return not_found()
			return jsonify(exams=json_result)
		else:
			return invalid_code()

@app.errorhandler(404)
def not_found(error=None):
    message = {
            'status': 404,
            'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404
    return resp

@app.errorhandler(404)
def invalid_code(error=None):
    message = {
            'status': 404,
            'message': 'Invalid course code: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404
    return resp


if __name__ == '__main__':
	app.run(debug=True,port=5002)
	