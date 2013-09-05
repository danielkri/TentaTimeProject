# -*- coding: utf-8 -*-
# URL validating class
# Currently validates course name and code using regex
# A: jhejdeurp
# TentaTimeProject
import re

"Functions for validating data"
class Validate(object):
	"RegEx validator, given a pattern and a sentence/word"		
	def validator(self,pattern,word):
		code_pattern = re.compile(pattern,re.I)
		if code_pattern.search(word):
			return True
		else: 
			return False
	"Validate coursecode"
	def courseCode(self,code):
		if len(code) == 6:
			return self.validator(r"^[a-zA-Z]{3}\d{1,4}$",code)
		else: 
			return False
	"Validate coursename"
	def courseName(self,name):
		if len(name) <= 120:
			return self.validator(r"[a-zA-Z]+",name)
		else: 
			return False