# -*- coding: utf-8 -*-
# A HTML parser that retrieves exam information from Studentportalen
# A: jhejdeurp
# TentaTimeProject
from lxml import html
from stuportalurl import getHTMLDoc
import re

#TODO: find a beutiful way to handle the parameters
#TODO: Make a test suite
#TODO: Parse the other stuportal link

"This function parses a html document"
def parse(code=None,name=None,offset=None,limit=None):
	#Request the html document 
	doc = getHTMLDoc(code=code,name=name,offset=offset,limit=limit)
	#Request the full result if necessary
	if offset is None and limit is None and getFullResultList(doc) > 20:
		doc = getHTMLDoc(code=code,name=name,offset=1,limit=getFullResultList(doc))
	#list with exam elements
	json_results = []

	#Get a list <td> elements for each exam
	#The first row is removed as well as the tableheader
	examdates_list = doc.xpath('.//*[@id="contentpage"]//table[3]/tr[not(@class="tableHeader") and position()>1]')
	
	for exam_el in examdates_list:

		if len(exam_el.xpath('.//td')) == 3:
			d = {'code' : exam_el.xpath('.//td[1]/a/text()')[0],
				 'url' :  exam_el.xpath('.//td[1]/a/@href')[0],
				 'ac_year' : exam_el.xpath('.//td[1]/a/@href')[0].split('ac_year=')[1],
				 'name' : exam_el.xpath('.//td[2]/text()')[0],
				 'element' : exam_el.xpath('.//td[2]/small/text()')[0],
				 'info' : exam_el.xpath('.//td[3]/text()')[0]}
			#add the result
			json_results.append(d)
		elif len(exam_el.xpath('.//td')) == 7:
			d = {'code' : exam_el.xpath('.//td[1]/a/text()')[0],
				 'url' :  exam_el.xpath('.//td[1]/a/@href')[0],
				 'ac_year' : exam_el.xpath('.//td[1]/a/@href')[0].split('ac_year=')[1],
				 'name' : exam_el.xpath('.//td[2]/text()')[0],
				 'element' : exam_el.xpath('.//td[2]/small/text()')[0],
				 'exam_date' : exam_el.xpath('.//td[3]/b/nobr/text()')[0],
				 'beginsAt' : exam_el.xpath('.//td[4]/center/text()')[0],
				 'location' : exam_el.xpath('.//td[5]/text()')[0],
				 'length' :  exam_el.xpath('.//td[6]/text()')[0],
				 'last_date_reg' : exam_el.xpath('.//td[7]/text()')[0],
				 'changes' : exam_el.xpath('.//td[7]/a/@href')}

			if len(d['changes']) >= 1:
				d['changes'][0] = "https://www.student.chalmers.se/sp/"+d['changes'][0]
			#add the result
			json_results.append(d)
	return json_results

"Gets the total number of found exams using"	
def getFullResultList(document):
	res_node = document.xpath('.//*[@id="contentpage"]//table[4]/tr/td[1]/b[2]/text()')
	#Split the string so we get the number of rows
	res = res_node [0].split(' av ')
	#return the result
	return int(res[1])


if __name__ == '__main__':

	json_list = parse(name="d")
	
	count = 0
	for el in json_list:
		count = count + 1
		print str(count) + ' of ' + str(len(json_list))
		print el['code'] + '\t' + el['name']
		#print el['exam_date']
		#print el['changes']






	

