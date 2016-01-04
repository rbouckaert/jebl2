package jebl.evolution.trees;

import jebl.evolution.graphs.Node;
import jebl.util.AttributableHelper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Common implementation of Attributable interface used by Nodes.
 *
 * @author Joseph Heled
 * @version $Id: BaseNode.java 956 2008-11-30 01:18:20Z rambaut $
 *
 */

public abstract class BaseNode implements Node {
    // Attributable IMPLEMENTATION

    @Override
	public void setAttribute(String name, Object value) {
        if (helper == null) {
            helper = new AttributableHelper();
        }
        helper.setAttribute(name, value);
    }

    @Override
	public Object getAttribute(String name) {
        if (helper == null) {
            return null;
        }
        return helper.getAttribute(name);
    }

    @Override
	public void removeAttribute(String name) {
        if( helper != null ) {
            helper.removeAttribute(name);
        }
    }

    @Override
	public Set<String> getAttributeNames() {
        if (helper == null) {
            return Collections.emptySet();
        }
        return helper.getAttributeNames();
    }

    @Override
	public Map<String, Object> getAttributeMap() {
        if (helper == null) {
            return Collections.emptyMap();
        }
        return helper.getAttributeMap();
    }

    // PRIVATE members

    private AttributableHelper helper = null;
}