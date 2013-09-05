# -*- coding: utf-8 -*-
# URL Handling for Studentportalen
# A: jhejdeurp
# TentaTimeProject
import requests
from lxml import html
from url_validate import Validate

"Artifical Enum for URL spedifications for Studentportalen (ENUM not yet supported on pre PEP-435)"
class Stupolen:
	URL = 'https://www.student.chalmers.se/sp/examdates_list?flag=1&sortorder=C_CODE&CM_CODE&EX_DATE'
	CODE = "&search_course_code="
	NAME = "&search_course_name="
	OFFSET = "&query_start="
	LIMIT = "&batch_size="

"Retrieves the HTML document"
def getHTMLDoc(**kwargs):
	#print buildBaseURL(**kwargs)
	#Get the webpage
	page = requests.get(buildBaseURL(**kwargs))
	#Raise an exception if the webpage is not valid or Error for 4xx or 5xx
	page.raise_for_status()
	#return a structured document
	return html.fromstring(page.text)

"Build the request URL"
def buildBaseURL(code=None,name=None,offset=None,limit=None):
	#Check first whether we have any arguments provided to the function
	if code is None and name is None:
		raise Exception("Neither a course name or a course code was provided")

	vdator = Validate()

	#Add parameters to perform the search on stuportalen
	if code is not None and name is None: #only coursecode
		if vdator.courseCode(code):
			return Stupolen.URL+Stupolen.CODE+code+addURLargs(offset,limit)
		else:
			raise Exception("Invalid: course code")
	elif code is None and name is not None: #only coursename
		if vdator.courseName(name):
			return Stupolen.URL+Stupolen.NAME+name+addURLargs(offset,limit)
		else: 
			raise Exception("Invalid: course name")
	elif code is not None and name is not None:
		if vdator.courseCode(code) and vdator.courseName(name):
			return Stupolen.URL+Stupolen.CODE+code+Stupolen.NAME+name+addURLargs(offset,limit)
		else:
			if not courseCode(code):
				raise Exception("Invalid: course code")
			else:
				raise Exception("Invalid: course name")

"Add arguments such as offset or limit to the URL"
def addURLargs(offset=None,limit=None):

	if offset is None and limit is None:
		return ""

	if offset is not None and limit is None: #only coursecode
		return Stupolen.OFFSET+str(offset)
	elif offset is None and limit is not None: #only coursename
		return Stupolen.LIMIT+str(limit)
	
	elif offset is not None and limit is not None: #both course code and name
		return Stupolen.OFFSET+str(offset)+Stupolen.LIMIT+str(limit)

if __name__ == '__main__':
	#Test course code
	getHTMLDoc(code='DAT050')
	#Test course name
	getHTMLDoc(name='matematik')
	#Test both course name and code
	getHTMLDoc(name='matematik',code="LMA033")
	#Test offset 
	getHTMLDoc(name='matematik',offset=20)
	#Test limit
	getHTMLDoc(name='matematik',limit=45)
	#Test limit and offset
	getHTMLDoc(name='matematik',limit=45, offset=3)



	





