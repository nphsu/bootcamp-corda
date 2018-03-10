![Corda](https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png)

# Bootcamp CorDapp

This project is the template we will use to define a complete CorDapp during 
today's bootcamp. Our CorDapp will allow a token to be issued onto the ledger.

We'll take a test-driven approach. You'll know your CorDapp is working 
when it passes both sets of tests defined in `src/test/kotlin/bootcamp`.

## Links to useful resources

This project contains two sets of example state and contract implementations:

* `src/main/kotlin/examples/ExampleStates.kt`
* `src/main/kotlin/examples/ExampleContract.kt`

There are also several web resources that you will likely find useful for this 
bootcamp:

* Key Concepts docs (`docs.corda.net/key-concepts.html`)
* API docs (`docs.corda.net/api-index.html`)
* Cheat sheet (`docs.corda.net/cheat-sheet.html`)
* Sample CorDapps (`www.corda.net/samples`)
* Stack Overflow (`www.stackoverflow.com/questions/tagged/corda`)

## What we'll be building

Our CorDapp will have three parts:

### The TokenState

States define shared facts on the ledger. Our state, TokenState, will define a 
token. It will have the following structure:

    -------------------
    |                 |
    |   TokenState    |
    |                 |
    |   - issuer      |
    |   - recipient   |
    |   - amount      |
    |                 |
    -------------------

### The TokenContract

Contracts govern how states evolve over time. Our contract, TokenContract, 
will define how TokenStates evolve. It will only allow the following type of 
TokenState transaction:

    -------------------------------------------------------------------------------------
    |                                                                                   |
    |    - - - - - - - - - -                                     -------------------    |
    |                                              ▲             |                 |    |
    |    |                 |                       | -►          |   TokenState    |    |
    |            NO             -------------------     -►       |                 |    |
    |    |                 |    |      Issue command       -►    |   - issuer      |    |
    |          INPUTS           |     signed by issuer     -►    |   - recipient   |    |
    |    |                 |    -------------------     -►       |   - amount > 0  |    |
    |                                              | -►          |                 |    |
    |    - - - - - - - - - -                       ▼             -------------------    |
    |                                                                                   |
    -------------------------------------------------------------------------------------

              No inputs             One issue command,                One output,
                                 issuer is a required signer       amount is positive

To do so, TokenContract will impose the following constraints on transactions 
involving TokenStates:

* The transaction has no input states
* The transaction has one output state
* The transaction has one command
* The output state is a TokenState
* The output state has a positive amount
* The command is an Issue command
* The command lists the TokenState's issuer as a required signer

### The TokenFlow

Flows automate the process of updating the ledger. Our flow, TokenFlow, will 
automate the following steps:

            Issuer                Recipient                Notary
              |                       |                       |
       Chooses a notary                                        
              |                       |                       |
        Starts building                                        
         a transaction                |                       |
              |                                                
        Adds the output               |                       |
          TokenState                                           
              |                       |                       |
           Adds the                                            
         Issue command                |                       |
              |                                                
         Verifies the                 |                       |
          transaction                                          
              |                       |                       |
          Signs the                                            
         transaction                  |                       |
              |                                                
              |----------------------------------------------►|
              |                       |                       |
                                                         Notarises the 
              |                       |                   transaction
                                                              |
              |◀----------------------------------------------|
              |                       |                       |
         Records the                                           
         transaction                  |                       |
              |                                                
              |----------------------►|                       |
                                      |                        
              |                  Records the                  |
                                 transaction                   
              |                       |                       |
              ▼                       ▼                       ▼

## Running our CorDapp

Normally, you'd interact with a CorDapp via a client or webserver. So we can
focus on our CorDapp, we'll be running it via the node shell instead.

Once you've finished the CorDapp's code, run it with the following steps:

* Build a test network of nodes by opening a terminal window at the root of
  your project and running the following command:

    * Windows, Kotlin code: `gradlew.bat deployNodesKotlin`
    * Windows, Java code:   `gradlew.bat deployNodesJava`
    * macOS, Kotlin code:   `./gradlew deployNodesKotlin`
    * macOS, Java code:     `./gradlew deployNodesJava`

* Start the nodes by running the following command:

    * Windows, Kotlin code: `kotlin\build\nodes\runnodes.bat`
    * Windows, Java code:   `java\build\nodes\runnodes.bat`
    * macOS, Kotlin code:   `kotlin/build/nodes/runnodes`
    * macOS, Java code:     `java/build/nodes/runnodes`

* Open the nodes are started, go to the terminal of Party A (not the notary!)
  and run the following command to issue 99 tokens to Party B:

    `flow start TokenFlow recipient: PartyB, amount: 99`

* You can now see the tokens in both nodes' vaults by running the following
  command in their respective terminals:

    `run vaultQuery contractStateType: bootcamp.TokenState`
