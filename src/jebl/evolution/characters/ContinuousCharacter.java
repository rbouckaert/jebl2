package jebl.evolution.characters;

import beast.core.Param;
import java.util.*;

import beast.core.BEASTObject;
import jebl.evolution.taxa.*;

/**
 * @author Stephen A. Smith
 *
 */

public class ContinuousCharacter extends BEASTObject implements Character{
	/**
	 * Constructs a basic ContinuousCharacter object with no taxa added yet
	 * @param name the name of the character
	 * @param desc the description of the character
	 */
	public ContinuousCharacter(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="desc", description="auto converted jebl2 parameter") String desc) {
		this.name = name;
		this.charType = CharacterType.CONTINUOUS;
		this.desc = desc;
		taxa = new HashSet <Taxon> ();
    }
	
	/**
	 * Constructs a basic ContinuousCharacter object with taxa added
	 * @param name the name of the character
	 * @param desc the description of the character
	 * @param taxa the Set<Taxon> containing the taxa
	 */
	public ContinuousCharacter(
		@Param(name="name", description="auto converted jebl2 parameter") String name,
		@Param(name="desc", description="auto converted jebl2 parameter") String desc,
		@Param(name="taxa", description="auto converted jebl2 parameter") Set<Taxon> taxa) {
		this.name = name;
		this.charType = CharacterType.CONTINUOUS;
		this.desc = desc;
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
	
	/**
	 * set the taxa for this character with a previously constructed Set<Taxon>
	 * @param taxa a Set<Taxon> of the taxa containing this character
	 */
	public void addTaxa(Set<Taxon>taxa){
		this.taxa = taxa;
	}
	
	@Override
	public void addTaxon(Taxon taxon){
		taxa.add(taxon);
	}
	
	@Override
	public Object getValue(Taxon taxon){
		double value = ((Double)taxon.getAttribute(name)).doubleValue();
		return value;
	}
	
	/**
	 * 
	 * @param taxon the taxon for which to get the standard error
	 * @return double of the standard error for the taxon
	 */	
	public double getSE(Taxon taxon){
		double value = 0.0;
		if(taxon.getAttribute(name+"SE") != null)
			value = ((Double)taxon.getAttribute(name+"SE")).doubleValue();
		return value;
	}
	
	@Override
	public Set<Taxon> getTaxa(){ return taxa; }
	
	private String desc;
	private String name;
	private CharacterType charType;
	private Set<Taxon> taxa;
	
	@Override
	public void initAndValidate() {
		// nothing to do
	}

	public void setTaxa(Set<Taxon> taxa) {
		this.taxa = taxa;
	}

}