# Introduction #

Grid model in GridMe conforms to the meta-model shown on the following figure.

![http://gridme.googlecode.com/svn/site/model.png](http://gridme.googlecode.com/svn/site/model.png)

The Grid model consists of a set of model elements. There are two element types available:
`Connection` and `GridElement`. Grid elements represent such entities like cluster, broker, user, task flow, data storage, etc. Connections are used to connect grid elements with each other. Each model element has an implementation attribute whose value must point to a fully qualified Java class name which defines the behavior of the element. Elements may also have parameters. Parameter is a named string value, which is used by the model element to initialize itself. The names, count and allowed values for parameters are handled by the use of Java annotations - the framework scans the implementation class and extracts parameter information on the fly.

## Behavior ##

![http://gridme.googlecode.com/svn/site/statemachine.png](http://gridme.googlecode.com/svn/site/statemachine.png)

Element behavior is defined by a simple state machine.
There are states and transitions. Entering or leaving a state the statemachine can execute actions.
Transition from one state to another is performed in the following priority (see the figure):

![http://gridme.googlecode.com/svn/site/transitions.png](http://gridme.googlecode.com/svn/site/transitions.png)

  * There is an empty transition
  * The guard is true
  * The delay timer has elapsed
  * There is a signal in the signal buffer

# Creating a custom element #

![http://gridme.googlecode.com/svn/site/implementation.png](http://gridme.googlecode.com/svn/site/implementation.png)

To create an element you have to extend the class `com.googlecode.gridme.runtime.GElement` if you want to create a new Grid element or `com.googlecode.gridme.runtime.GConnection` if you want to create a connection.

To add a behavior you need to create a statemachine model, generate the code from it and use it inside your implementation class.

Open your project in eclipse, and create `new/Statemachine Diagram`.

![http://gridme.googlecode.com/svn/site/stm1.png](http://gridme.googlecode.com/svn/site/stm1.png)

Enter diagram name.

![http://gridme.googlecode.com/svn/site/stm2.png](http://gridme.googlecode.com/svn/site/stm2.png)

The model editor will open.

![http://gridme.googlecode.com/svn/site/stm3.png](http://gridme.googlecode.com/svn/site/stm3.png)

Create a model diagram.

![http://gridme.googlecode.com/svn/site/stm4.png](http://gridme.googlecode.com/svn/site/stm4.png)

To set or change properties do the following. Right click the model file and select `open with/other`. Select `Statemachine model editor` and press ok.

![http://gridme.googlecode.com/svn/site/stm5.png](http://gridme.googlecode.com/svn/site/stm5.png)

The model editor will open where you can edit properties.

![http://gridme.googlecode.com/svn/site/stm6.png](http://gridme.googlecode.com/svn/site/stm6.png)

For example, to create a transition delay right click the t2 node and select `New Child/Delay Long Value`.

![http://gridme.googlecode.com/svn/site/stm7.png](http://gridme.googlecode.com/svn/site/stm7.png)

Enter value.

![http://gridme.googlecode.com/svn/site/stm8.png](http://gridme.googlecode.com/svn/site/stm8.png)

Save the model and right click the model file in the package explorer. Select generate. Select the destination folder and click ok.

![http://gridme.googlecode.com/svn/site/stm9.png](http://gridme.googlecode.com/svn/site/stm9.png)

Now you can open the generated class which will also contain the usage instructions.

![http://gridme.googlecode.com/svn/site/stm10.png](http://gridme.googlecode.com/svn/site/stm10.png)