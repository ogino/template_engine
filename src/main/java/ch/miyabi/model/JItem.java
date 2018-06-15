package ch.miyabi.model;

import java.util.Map;

public class JItem {

    private Integer id;
    private String name;
    private Map<String, Object> attribute;

    public JItem(final Integer id, final String name, final Map<String, Object> attribute) {
        this.id = id;
        this.name = name;
        this.attribute = attribute;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Object> getAttribute() {
        return this.attribute;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", name = " + name +
                ", attribute = " + attribute;
    }
}
