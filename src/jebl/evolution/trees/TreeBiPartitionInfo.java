package jebl.evolution.trees;

import beast.core.BEASTObject;
import beast.core.Param;
import jebl.evolution.graphs.Node;
import jebl.evolution.taxa.Taxon;
import jebl.util.FixedBitSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Work in progress
 * @author Joseph Heled
 * @version $Id: TreeBiPartitionInfo.java 923 2008-06-15 20:45:11Z pepster $
 */
public class TreeBiPartitionInfo extends BEASTObject {
    class BiPartiotionInfo {
        BiPartiotionInfo(Node n) {
            this.n = n;
        }

        Node n;
        public boolean has;
    }

    List<Taxon> taxa;
    RootedTree t;
    final int        nTips;
    HashMap<FixedBitSet, BiPartiotionInfo> all;

    public TreeBiPartitionInfo(
		@Param(name="t", description="auto converted jebl2 parameter") RootedTree t,
		@Param(name="taxa", description="auto converted jebl2 parameter") List<Taxon> taxa) {
        this.t = t;
        this.taxa = taxa;
        nTips = t.getExternalNodes().size();
        all = new LinkedHashMap<>();
        forNode(t.getRootNode());
    }


//    BiPartiotionInfo forNode(Node n) {
//        final BiPartiotionInfo p = new BiPartiotionInfo(n);
//        if( t.isExternal(n) ) {
//            final int pos = taxa.indexOf(t.getTaxon(n));
//            p.partition.set(pos);
//
//        } else {
//
//            for( Node c : t.getChildren(n) ) {
//                final TreeBiPartitionInfo.BiPartiotionInfo info = forNode(c);
//                p.partition.union(info.partition);
//            }
//        }
//        if( ! p.partition.contains(0)  ) {
//            p.partition.complement();
//        }
//        all.put(p.partition, n);
//        return p;
//    }
//

    private FixedBitSet forNode(Node n) {
        final FixedBitSet p = new FixedBitSet(nTips);
        if( t.isExternal(n) ) {
            final int pos = taxa.indexOf(t.getTaxon(n));
            p.set(pos);

        } else {

            for( Node c : t.getChildren(n) ) {
                final FixedBitSet info = forNode(c);
                p.union(info);
            }
        }
        boolean wasComplemented = false;
        if( t.getParent(n) != t.getRootNode() && ! p.contains(0)  ) {
            p.complement();
            wasComplemented = true;
        }

        all.put(p, new BiPartiotionInfo(n));

        if( wasComplemented ) {
            p.complement();
        }
        return p;
    }

    public enum DistanceNorm {
        NORM1,
        NORM2
    }

    public static double distance(TreeBiPartitionInfo t1, TreeBiPartitionInfo t2, DistanceNorm norm) {

        for( BiPartiotionInfo k : t2.all.values() ) {
            k.has = false;
        }
        double din = 0;
        double dout = 0;

        for( Map.Entry<FixedBitSet, BiPartiotionInfo> k : t1.all.entrySet() ) {
            final BiPartiotionInfo info = t2.all.get(k.getKey());
            final double b1 = t1.t.getLength(k.getValue().n);
            double dif;
            if( info != null ) {

                final double b2 = t2.t.getLength(info.n);
                info.has = true;

                dif = Math.abs(b1 - b2);
            } else {
                dif = b1;
            }
            if( norm == DistanceNorm.NORM1 ) {
                //d += dif;
                din += dif;
            } else {
                //d += dif * dif;
                din += dif * dif;
            }
        }

        for( BiPartiotionInfo info : t2.all.values() ) {
            if( !info.has ) {
                final double dif = t2.t.getLength(info.n);
                if( norm == DistanceNorm.NORM1 ) {
                    //d += dif;
                    dout += dif;
                } else {
                    //d += dif * dif;
                    dout += dif * dif;
                }
            }
        }
        double d = din + dout;
        return ( norm == DistanceNorm.NORM1 ) ? d : Math.sqrt(d);
    }

	public RootedTree getT() {
		return t;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setT(RootedTree t) {
		this.t = t;
	}

	public List<Taxon> getTaxa() {
		return taxa;
	}

	/** should not be used other than by BEAST framework **/
	@Deprecated
	public void setTaxa(List<Taxon> taxa) {
		this.taxa = taxa;
	}

	public void setTaxa(Taxon taxa) {
		this.taxa.add(taxa);
	}

	@Override
	public void initAndValidate() {
		// nothing to do
		
	}

}