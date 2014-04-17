DESCRIPTION OF THE CODE:


Constants.java
This has a list of all the PATHS and VARIABLES used by the other parts of the Project.

StopWords.txt
List Of StopWords

VecTextField.java
This was added as a part of missing subset of Lucene 4.0

TextFileIndexer.java
This is Indexes the Dataset. Uses Lucene to create Index.

Reader.java
This gives the interface to run Queries.
This is where we expand the query and show the modified results.

EnumerateDocuments.java
This gives unique id numbers to all the documents in the Dataset.
This ID numbers are used in calculating Similarity Matrix and referencing documents in the future

CreateIndividualIndex.java
This Creates Indexes of all the individual files of the Dataset using Lucene. This is used to fetch the Term Frequency Vector for each document.

CosineSimilarity.java
This is decrepated version of out attempt to find Cosine Similarity.
CosineDocumentSimilarity_old.java
This is decrepated version of out attempt to find Cosine Similarity.

CosineDocumentSimilarity.java
This finds Cosine Similarity between each pair of documents and generates the similarity matrix.

ClusterTags.java
This Finds the Top Terms of each Cluster


EXTERNAL TOOLS:

We used Lucene4.0 to create and read Indexes.
We used Cluto2.1 to create Clustering.

HOW TO RUN:
1) Set Appropriate paths in Constants.java
2) RUn TextFileIndexer.java to create Search Indexes.
3) Run EnumerateDocuments.java to give numbers to Docs.
4) Run CreateIndividualIndex.java to create Similarity Matrix
5) Run scluster command of Cluto to generate Cluster File/
6) Run ClusterTags.java to form Top Terms of each cluster
7) Run Reader.java to run queries and get Expanded Queries and its corresponding modified result.