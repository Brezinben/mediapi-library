package org.stcharles.jakartatp.api.Group;

import com.sun.xml.internal.ws.developer.Serialization;
import org.stcharles.jakartatp.model.Group;

import java.time.LocalDate;

@Serialization(encoding = "json")
public class GroupOutput {
    public String name;
    public LocalDate created_at;
    public Integer id;

    public GroupOutput(Group g) {
        this.id = g.getId();
        this.name = g.getName();
        this.created_at = g.getCreated_at();
    }
}
