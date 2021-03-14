package org.stcharles.jakartatp.api.Item;

import com.sun.xml.internal.ws.developer.Serialization;
import org.stcharles.jakartatp.model.Item;
import org.stcharles.jakartatp.model.ItemState;
import org.stcharles.jakartatp.model.ItemType;

import java.time.LocalDate;
import java.util.Optional;

@Serialization(encoding = "json")
public class ItemOutput {
    public ItemState state;
    public ItemType type;
    public LocalDate cratedAt;
    public Integer id;
    public Integer album;
    public Integer loan;

    /**
     * @param i
     */
    public ItemOutput(Item i) {
        this.state = i.getState();
        this.type = i.getType();
        this.cratedAt = i.getCratedAt();
        this.id = i.getId();
        this.album = i.getAlbum().getId();
        this.loan = Optional.ofNullable(i.getLoan()).isPresent() ? i.getLoan().getId() : 0;
    }

}
