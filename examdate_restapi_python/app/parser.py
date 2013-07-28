# Proof of Concept - Parse a Studentportal HTML file
# A: jhejdeurp
# TentaTimeProject
from lxml import html
import requests


#This URL retrieves all course codes that contains a "D" and the list is sorted on Course codes
test_url = "https://www.student.chalmers.se/sp/examdates_list?flag=1;sortorder=C_CODE,CM_CODE,EX_DATE;search_course_code=dat050"

#Get the webpage
page = requests.get(test_url)
#Create a structured document
doc = html.fromstring(page.text)

#Get a list <td> elements for each exam
#The first row is removed as well as the tableheader
examdates_list = doc.xpath('.//*[@id="contentpage"]//table[3]/tr[not(@class="tableHeader") and position()>1]')


print html.tostring(examdates_list[0])


