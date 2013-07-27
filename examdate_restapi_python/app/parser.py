# Proof of Concept - Parse a Studentportal HTML file
# A: jhejdeurp
# TentaTimeProject
import lxml.html
from lxml.html import fromstring
from lxml import etree
import urllib
#This URL retrieves all course codes that contains a "D" and the list is sorted on Course codes
test_url = "https://www.student.chalmers.se/sp/examdates_list?flag=1;sortorder=C_CODE,CM_CODE,EX_DATE;search_course_code=d"
#Get the content
content = urllib.urlopen(test_url).read()
#Reconstruct the content to a HTML doc
doc = fromstring(content)
doc.make_links_absolute(test_url)

#Retrieve the element with contentpage
el = doc.get_element_by_id('contentpage')

print el.text_content()
