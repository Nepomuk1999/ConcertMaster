package team_f.client.pages;

import java.util.List;

public abstract class BaseTablePage<R, V, L, S> extends BasePage<R, V, L, S> {
    protected PageAction<R, V> _onChange;
    protected PageAction<List<L>, S> _loadList;

    public void setOnListChange(PageAction<R, V> action) {
        _onChange = action;
    }

    public void setOnLoadList(PageAction<List<L>, S> action) {
        _loadList = action;
    }
}
