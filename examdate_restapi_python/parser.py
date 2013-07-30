# -*- coding: utf-8 -*-
# Proof of Concept - Parse a Studentportal HTML file
# A: jhejdeurp
# TentaTimeProject
from lxml import html
import requests


# TODO: fixa fallet med DAT042 som har ett varningmarke pa den sista <td>

#This URL retrieves all course codes that contains a "D" and the list is sorted on Course code
test_url = "https://www.student.chalmers.se/sp/examdates_list?flag=1;sortorder=C_CODE,CM_CODE,EX_DATE;search_course_code=d"
#test_url = 'https://www.student.chalmers.se/sp/examdates_list?flag=1&query_start=21&batch_size=20&sortorder=C_CODE,CM_CODE,EX_DATE&search_course_code=d'

#Get the webpage
page = requests.get(test_url)
#Create a structured document
doc = html.fromstring(page.text)

#Get a list <td> elements for each exam
#The first row is removed as well as the tableheader
examdates_list = doc.xpath('.//*[@id="contentpage"]//table[3]/tr[not(@class="tableHeader") and position()>1]')

nav_info = doc.xpath('.//*[@id="contentpage"]//table[4]/tr/td[2]')


if len(nav_info) is 1 and "Nästa" in nav_info[0].xpath('.//a[1]/text()')[0].encode('utf-8'):
	print nav_info[0].xpath('.//a[1]/@href')[0]
elif len(nav_info) is 1 and "Nästa" in nav_info[0].xpath('.//a[2]/text()')[0].encode('utf-8'):
	print nav_info[0].xpath('.//a[2]/@href')[0]
	print nav_info[0].xpath('.//a[2]/text()')[0].encode('utf-8')


for exam_el in examdates_list:
	print exam_el.xpath('.//td[1]/a/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[1]/a/@href')[0]	
	print exam_el.xpath('.//td[2]/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[2]/small/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[3]/b/nobr/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[4]/center/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[5]/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[6]/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[7]/text()')[0].encode('utf-8')
	print exam_el.xpath('.//td[7]/a/@href')  

#print examdates_list
#print html.tostring(nav_info[0])


