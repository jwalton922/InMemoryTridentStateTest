InMemoryTridentStateTest
========================

I was having issues with an in memory Trident State with more than one executor. I posted a question to the Storm dev email group.

Here's the email:

I have an in memory, Java database that I want to use for my Trident State. My testing consists of running a limited set of data through the topology, and then querying the results through a DRPC stream that performs a Trident state query.
When the trident state is in a single executor, the same state query returns the same data every time. When the trident state is in a bolt with multiple executors, I get X different results for the same state query where X is the number of executors containing the trident state.  

Is there a way to specify which trident state is used for a DRPC query? Is there a way to get the results (and aggregate) from the all of the different trident state instances?

So I created a sample project in which to show the problem. Here's the repository: https://github.com/jwalton922/InMemoryTridentStateTest

I submit two topologies like:

storm jar TestTopology/target/TestTopology-1.0-SNAPSHOT-jar-with-dependencies.jar com.mrcy.testtopology.TestTopologyBuilderry 1

storm jar TestTopology/target/TestTopology-1.0-SNAPSHOT-jar-with-dependencies.jar com.mrcy.testtopology.TestTopologyBuilderry 2

When I query the first topology (with parallelism 1) I get the following output indefinitely like:

DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
DRPC result: [["",["event 1","event 2","event 3","event 1"]]]
When I query the topology with parallelism of 2, I get output like:
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 1","event 3"]]]
DRPC result: [["",["event 1","event 3"]]]
DRPC result: [["",["event 1","event 3"]]]
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 1","event 3"]]]
DRPC result: [["",["event 1","event 3"]]]
DRPC result: [["",["event 1","event 3"]]]
DRPC result: [["",["event 2","event 1"]]]
DRPC result: [["",["event 2","event 1"]]]
As you can see, when the in memory trident state is in more than one executor, you don't know which state will be queried, and your results will potentially differ between DRPC queries.

My real problem involves hosting an in memory graph database. I'd like to be able to partition the graph by some vertex property, but then I would need to be able to query across multiple executors of my trident state so that I could return vertices and edges cross partitions.   

