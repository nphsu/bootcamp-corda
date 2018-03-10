package examples;

import com.esotericsoftware.kryo.NotNull;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;

import java.util.List;


//public class HouseState implements ContractState {
//
//    private final String address;
//
//    private final Party owner;
//
//    private final Party mortgageLender;
//
//    public HouseState(String address, Party owner, Party mortgageLender) {
//        this.address = address;
//        this.owner = owner;
//        this.mortgageLender = mortgageLender;
//    }
//
//    @Override
//    public List<AbstractParty> getParticipants() {
//        return null;
//    }
//}

// The simplest form of state.
// Implements ContractState (all states must implement ContractState or a sub-interface).
public class IAmAState implements ContractState {
    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of();
    }
}