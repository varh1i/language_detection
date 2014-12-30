#!/usr/bin/env python
# eval.py
# by Marco Lui, mlui@pgrad.unimelb.edu.au, 28/07/2010
# Supplied with naacl2010-langid dataset
 
import sys
import csv
from collections import defaultdict
from optparse import OptionParser

def fscore(p, r, beta=1.0):
  "Compute f-score, aka f1 Measure"
  f = (1 + beta ** 2) * p * r / (beta**2 * p + r)
  return f

def prf(tp=0,fp=0,fn=0, beta=1.0):
  "Compute precision, recall and f-score"
  p = float(tp) / (tp+fp)
  r = float(tp) / (tp+fn)
  f = fscore(p,r,beta)
  return (p,r,f)

if __name__ == "__main__":

  # Parse command-line
  parser = OptionParser()
  parser.add_option("-g","--goldstandard",dest="gs",help="Goldstandard",metavar="FILE")
  parser.add_option("-c","--classif",dest="cl",help="Classifier output",metavar="FILE")

  options, args = parser.parse_args()
  if len(args) != 0:
    parser.error("no arguments expected")
  if options.gs is None:
    parser.error("no goldstandard file specified")
  if options.cl is None:
    parser.error("no classifications file specified")

  # Parse the goldstandard file
  goldstandard = dict()
  for row in csv.reader(open(options.gs), delimiter='\t'):
    docid = row[0]
    klass = row[2]
    goldstandard[docid] = klass

  # Parse the classifier output
  classif = dict()
  for row in csv.reader(open(options.cl), delimiter='\t'):
    docid = row[0]
    klass = row[1]
    classif[docid] = klass

  # Check that the document identifiers match in the two files
  if set(goldstandard) != set(classif):
    raise ValueError, "Document identifier mismatch"

  # We only compute a partial confusion matrix. We skip accounting for True Negatives,
  # since in a 1-of-M classification this count is implicit and always relatively large.
  perclass_confusion_matrix = defaultdict(lambda:dict(tp=0, fp=0, fn=0))

  for docid in goldstandard:
    gs = goldstandard[docid]
    cl = classif[docid]

    if gs == cl:
      perclass_confusion_matrix[gs]['tp'] += 1
    else:
      perclass_confusion_matrix[gs]['fn'] += 1
      perclass_confusion_matrix[cl]['fp'] += 1

  micro_confusion_matrix = dict(tp=0, fp=0, fn=0)
  macro_p_tally = 0.0
  macro_r_tally = 0.0

  # Add up the precision and recall values to compute a macro-average,
  # and add up the confusion matrices to compute a micro-average
  for class_matrix in perclass_confusion_matrix.values():
    p,r,f = prf(**class_matrix)
    macro_p_tally += p
    macro_r_tally += r
    for key in class_matrix:
      micro_confusion_matrix[key] += class_matrix[key]

  # Compute scores. Only a single micro score is computed, since 
  # for a 1-of-M classification every false positive is also a false
  # negative, so precision = recall = fscore
  macro_p = macro_p_tally / len(perclass_confusion_matrix)
  macro_r = macro_r_tally / len(perclass_confusion_matrix)
  macro_f = fscore(macro_p, macro_r)
  micro_score = prf(**micro_confusion_matrix)[0]


  # Display summary
  print "micro-averaged prf:       %.3f" % micro_score 
  print "macro-averaged precision: %.3f" % macro_p
  print "macro-averaged recall:    %.3f" % macro_r
  print "macro-averaged f-score:   %.3f" % macro_f
