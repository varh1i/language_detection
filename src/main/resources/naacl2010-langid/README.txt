RESOURCE: MONOLINGUAL LANGUAGE IDENTIFICATION


VERSION: 1.0


DESCRIPTION: 

This is the dataset used for language identification described in:

  Baldwin, Timothy and Marco Lui (2010) Language Identification: The Long and
  the Short of the Matter.  In Human Language Technologies: The 2010 Annual
  Conference of the North American Chapter of the Association for
  Computational Linguistics.  Los Angeles, USA, pp. 229-237.

It contains three sub-corpora:

  EuroGOV: derived from the WebCLEF EuroGOV document collection

  Wikipedia: sourced from wikipedia.org

  TCL: originally constructed by the Thai Computational Linguistics Laboratory

The three datasets are provided as is for research purposes only, without any
claim to copyright of the data.

Each folder in the dataset is named according to name used in the paper, and
contains a number of text documents. Metadata about the documents is stored in
'meta' files, which are tab-delimited listings in the following format:

<filename> <encoding> <iso639-1 language code> <partition>

10-fold cross-validation was used in our experiments. Therefore, in the
interest of accountability we include sufficient information to reconstruct
our original partitioning.




EVALUATION:

An evaluation script is included with the dataset, which calculates micro- and
macro-averaged precision, recall and F-score values a la Baldwin and Lui
(2010). The evaluation script is called as follows:

# python eval.py -g GOLD -c OUT

where GOLD is the 'meta' file (e.g. 'Wikipedia.meta'), and OUT contains the
predictions for all documents in the sub-corpus of interest, in the following
format:

<filename> <iso639-1 language code>

with a tab delimiter.



LICENSE:

This dataset is made available under the terms of the Creative Commons
Attribution 3.0 Unported licence
(http://creativecommons.org/licenses/by/3.0/), with attribution via citation
of the following paper, which describes the dataset in full detail:

  Baldwin, Timothy and Marco Lui (2010) Language Identification: The Long and
  the Short of the Matter.  In Human Language Technologies: The 2010 Annual
  Conference of the North American Chapter of the Association for
  Computational Linguistics.  Los Angeles, USA, pp. 229-237.

The paper can be found in the PDF at:

http://www.aclweb.org/anthology/N10-1027

While the Thai Computational Linguistics Laboratory constructed the TCL
dataset, they bear no responsibility for its distribution.



ACKNOWLEDGEMENTS:

Many thanks to Baden Hughes with the EuroGOV collection, and Virach
Sornlertlamvanich and Canasai Kruengkrai for providing a copy of the TCL data.



CONTACTS:

Marco Lui (mlui@pgrad.unimelb.edu.au)
Tim Baldwin (tb@ldwin.net)
