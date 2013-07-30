# -*- coding: utf-8 -*-
# A HTML parser that retrieves exam information from Studentportalen
# A: jhejdeurp
# TentaTimeProject
from lxml import html
import requests
import re

"Parses a studenportal html and returns a list in json format"
def parse(stuportal_url):
	#Get the webpage
	page = requests.get(stuportal_url)
	#Raise an exception if the webpage is not valid or Error for 4xx or 5xx
	page.raise_for_status()
	#Create a structured document
	doc = html.fromstring(page.text)

	#list with exam elements
	json_results = []
	#compile pattern
	patt = re.compile('\w+')

	#Get a list <td> elements for each exam
	#The first row is removed as well as the tableheader
	examdates_list = doc.xpath('.//*[@id="contentpage"]//table[3]/tr[not(@class="tableHeader") and position()>1]')
	

	for exam_el in examdates_list:

		if len(exam_el.xpath('.//td')) == 3:
			words = patt.findall(exam_el.xpath('.//td[2]/text()')[0].encode('utf-8'))
			info =  patt.findall(exam_el.xpath('.//td[3]/text()')[0].encode('utf-8'))
			d = {'code' : exam_el.xpath('.//td[1]/a/text()')[0].encode('utf-8'),
				 'url' :  exam_el.xpath('.//td[1]/a/@href')[0],
				 'ac_year' : exam_el.xpath('.//td[1]/a/@href')[0].split('ac_year=')[1],
				 'name' : " ".join(words),
				 'element' : exam_el.xpath('.//td[2]/small/text()')[0].encode('utf-8'),
				 'info' : " ".join(info)}
			#add the result
			json_results.append(d)
		elif len(exam_el.xpath('.//td')) == 7:
			words = patt.findall(exam_el.xpath('.//td[2]/text()')[0].encode('utf-8'))
			date = patt.findall(exam_el.xpath('.//td[7]/text()')[0].encode('utf-8'))
			d = {'code' : exam_el.xpath('.//td[1]/a/text()')[0].encode('utf-8'),
				 'url' :  exam_el.xpath('.//td[1]/a/@href')[0],
				 'ac_year' : exam_el.xpath('.//td[1]/a/@href')[0].split('ac_year=')[1],
				 'name' : " ".join(words),
				 'element' : exam_el.xpath('.//td[2]/small/text()')[0].encode('utf-8'),
				 'exam_date' : exam_el.xpath('.//td[3]/b/nobr/text()')[0].encode('utf-8'),
				 'beginsAt' : exam_el.xpath('.//td[4]/center/text()')[0].encode('utf-8'),
				 'location' : exam_el.xpath('.//td[5]/text()')[0].encode('utf-8'),
				 'length' :  exam_el.xpath('.//td[6]/text()')[0].encode('utf-8'),
				 'last_date_reg' : " ".join(date),
				 'changes' : exam_el.xpath('.//td[7]/a/@href')}

			if len(d['changes']) >= 1:
				d['changes'][0] = "https://www.student.chalmers.se/sp/"+d['changes'][0]
			#add the result
			json_results.append(d)


	#Pagination handling for studenportalen
	pagination = doc.xpath('.//*[@id="contentpage"]//table[4]/tr/td[2]')
	#Check whether we can navigate to a "Nästa"
	# if so we recusrively apply until we hit a "föregående"
	if len(pagination[0].xpath('.//a[1]/text()')) is not 0 and "Nästa" in pagination[0].xpath('.//a[1]/text()')[0].encode('utf-8'):
		return json_results + parse(pagination[0].xpath('.//a[1]/@href')[0])
	elif len(pagination[0].xpath('.//a[2]/text()')) is not 0 and "Nästa" in pagination[0].xpath('.//a[2]/text()')[0].encode('utf-8'):
		return json_results + parse(pagination[0].xpath('.//a[2]/@href')[0])
	else:
		return json_results

"Gets the total number of found exams using"	
def getNumberOfResults(stuportal_url):
	#Get the webpage
	page = requests.get(stuportal_url)
	#Raise an exception if the webpage is not valid or Error for 4xx or 5xx
	page.raise_for_status()
	#Create a structured document
	doc = html.fromstring(page.text)
	#Get the node with info about number of rows
	res_node = doc.xpath('.//*[@id="contentpage"]//table[4]/tr/td[1]/b[2]/text()')
	#Split the string so we get the number of rows
	res = res_node [0].split(' av ')
	#return the result
	return int(res[1])

"The fast mode avoids pagination and requests the whole result table at once"	
def parseFast(stuportal_url):
	#Get total number of result rows
	tot_rows = getNumberOfResults(stuportal_url)
	#modify the url
	new_args= "&query_start=1&batch_size=" + str(tot_rows)
	
	new_url = stuportal_url + new_args
	print new_url
	return parse(new_url)


def validateCoursecode(code):
	if len(code) == 6:
		code_pattern = re.compile(r"^[A-Z]{3}\d{1,4}$",re.I)

		if code_pattern.search(code):
			return True
		else:
		 	return False
	else:
		return False

if __name__ == '__main__':

	json_list = parseFast('https://www.student.chalmers.se/sp/examdates_list?flag=1;sortorder=C_CODE,CM_CODE,EX_DATE;search_course_code=c')
	#assert len(json_list) == 179

	count = 0
	for el in json_list:
		count = count + 1
		print str(count) + ' of ' + str(len(json_list))
		print el['code']
		#print el['exam_date']
		#print el['changes']





	


      
