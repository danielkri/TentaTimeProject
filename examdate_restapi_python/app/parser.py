# Proof of Concept - Parse a Studentportal HTML file
# A: jhejdeurp
# TentaTimeProject
from lxml import html
import urllib


#This URL retrieves all course codes that contains a "D" and the list is sorted on Course codes
test_url = "https://www.student.chalmers.se/sp/examdates_list?flag=1;sortorder=C_CODE,CM_CODE,EX_DATE;search_course_code=dat050"

#Get the content
content = urllib.urlopen(test_url).read()
doc = html.fromstring(content)
doc.make_links_absolute(test_url)

content = doc.xpath('.//*[@id="contentpage"]//table[3]/tr[not(@class="tableHeader") and position()>1]')



print html.tostring(content[0])


