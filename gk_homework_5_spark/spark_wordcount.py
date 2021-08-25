#!/bin/env python
#encoding=utf-8

from operator import add
from pyspark import SparkContext

def word_count():
    sc = SparkContext(appName="wordcount")
    textFile = sc.textFile("/root/cx/test_file/")
    result = textFile.flatMap(lambda x: x.split(" ")).map(lambda x: (x,1)).reduceByKey(add).sortBy(lambda x: x[1], False).take(5)
    for k,v in result:
        print(k,v)

if __name__=='__main__':
    word_count()