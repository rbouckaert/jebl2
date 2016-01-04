package jebl.evolution.characters;

import beast.core.Param;
import java.util.*;

import beast.core.BEASTObject;
import jebl.evolution.taxa.*;

/**
 * @author Stephen A. Smith
 *
 */
public class DiscreteCharacter extends BEASTObject implements Character{

	/**
	 * Constructs a basic DiscreteCharacter object with no taxa added yet
	 * @param name the name of the character
	 * @param desc the description of the character
	 * @param numOfStates the number of possible states for the character
	 */
	public DiscreteCharacter(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="desc", description="auto converted jebl2 parameter") String desc,
		@Param(name="numOfStates", description="auto converted jebl2 parameter") Integer numOfStates) {
		this.name = name;
		this.charType = CharacterType.DISCRETE;
		this.desc = desc;
		this.numOfStates = numOfStates;
		this.taxa = new HashSet <Taxon> ();
    }
	/**
	 * Constructs a basic DiscreteCharacter object with taxa
	 * @param name the name of the character
	 * @param desc the description of the character
	 * @param numOfStates the number of possible states for the character
	 * @param taxa the Set<Taxon> containing the taxa with this character
	 */
	public DiscreteCharacter(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="desc", description="auto converted jebl2 parameter") String desc,
		@Param(name="numOfStates", description="auto converted jebl2 parameter") Integer numOfStates,
		@Param(name="taxa", description="auto converted jebl2 parameter") Set<Taxon> taxa) {
		this.name = name;
		this.charType = CharacterType.DISCRETE;
		this.desc = desc;
		this.numOfStates = numOfStates;
		this.taxa = taxa;
    }
	
	@Override
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String getName(){ return name; }
	
	@Override
	public void setDesc(String desc){
		this.desc = desc;
	}
	
	@Override
	public String getDesc(){ return desc; }
	
	@Override
	public CharacterType getType(){
		return charType;
	}
	
	@Override
	public void addTaxon(Taxon taxon){
		taxa.add(taxon);
	}
	
	@Override
	public Object getValue(Taxon taxon){
		int value = ((Integer)taxon.getAttribute(name)).intValue();
		return value;
	}
	
	/**
	 * @return whether character is ordered or not
	 */
	public boolean isOrdered(){ return isOrdered;}
	
	/**
	 * 
	 * @param isOrdered set whether character is ordered or not
	 */
	public void setIsOrdered(boolean isOrdered){
		this.isOrdered = isOrdered;
	}
	
	/**
	 * 
	 * @return the number of possible states for the character
	 */
	public double getNumOfStates(){ return numOfStates; }
	
	/**
	 * 
	 * @param numOfStates the number of possible states for the characeter
	 */
	public void setNumOfStates(int numOfStates){
		this.numOfStates = numOfStates;
	}
	
	@Override
	public Set<Taxon> getTaxa(){ return taxa; }
	
	/**
	 * 
	 * @param stateDesc a Map<Integer, String> of the state descriptions corresponding to the values
	 */
	public void setStateDesc(Map <Integer, String> stateDesc){
		this.stateDesc = stateDesc;
	}
	
	/**
	 * 
	 * @return the Map<Integer, String> of the state descriptions corresponding to the values
	 */
	public Map <Integer, String> getStateDesc(){ return stateDesc; }
	
	/**
	 * 
	 * @param state corresponding to the state
	 * @return state description
	 */
	public String getStateDesc(int state){
		return stateDesc.get(state);
	}
	
	private boolean isOrdered;
	private String name;
	private String desc;
	private CharacterType charType;
	private Set<Taxon> taxa;
	private Map<Integer, String> stateDesc;
	private int numOfStates;

	@Override
	public void initAndValidate() throws Exception {
		// nothing to do
	}


	public void setNumOfStates(Integer numOfStates) {
		this.numOfStates = numOfStates;
	}

	public void setTaxa(Set<Taxon> taxa) {
		this.taxa = taxa;
	}

}