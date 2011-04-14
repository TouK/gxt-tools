package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers.*;


public class ViewMemberFactory {

    public static HasValue<String> createHasValue(final Label label) {
        return new HasValue<String>() {

            
            public void setValue(String value) {
                label.setText(value);
            }

            
            public String getValue() {
                return label.getText();
            }
        };
    }

    public static HasValue<String> createHasValue(final Button button) {
        return new HasValue<String>() {

            
            public void setValue(String value) {
                button.setText(value);
            }

            
            public String getValue() {
                return button.getText();
            }
        };
    }

    public static HasValue<String> createHasValue(final TextField<String> note) {
        return new HasValue<String>() {

            
            public void setValue(String value) {
                note.setValue(value);
            }

            
            public String getValue() {
                return note.getValue();
            }
        };
    }

    public static IsLikeButton createIsLikeButton(final Button button) {
        return new IsLikeButton() {

            
            public String getText() {
                return button.getText();
            }

            
            public boolean isEnabled() {
                return button.isEnabled();
            }

            
            public void setEnabled(boolean enabled) {
                button.setEnabled(enabled);
            }

            
            public void setText(String text) {
                button.setText(text);
            }

            
            public void addHandler(final Handler handler) {
                button.getListeners(Events.Select).clear();
                button.addSelectionListener(new SelectionListener<ButtonEvent>() {

                    
                    public void componentSelected(ButtonEvent ce) {
                        handler.onSelected(ce);
                    }
                });
            }
        };
    }

    public static HasSelected<ButtonEvent> createHasSelected(final Button button) {
        return new HasSelected<ButtonEvent>() {

            
            public void addHandler(final Handler<ButtonEvent> handler) {
                button.getListeners(Events.Select).clear();
                button.addSelectionListener(new SelectionListener<ButtonEvent>() {

                    
                    public void componentSelected(ButtonEvent ce) {
                        handler.onSelected(ce);
                    }
                });
            }
        };
    }

    public static HasDataLoaded<LoadEvent> createHasDataLoaded(final BasePagingLoader loader) {
        return new HasDataLoaded<LoadEvent>() {

            
            public void addHandler(final Handler handler) {
            	loader.addLoadListener(new LoadListener() {

                    
                    public void handleEvent(LoadEvent e) {
                        super.handleEvent(e);
                        handler.onSelected(e);
                    }

                });
            }
        };
    }

    public static HasSelected<SelectionChangedEvent> createSelectionChange(final Grid grid) {
        return new HasSelected<SelectionChangedEvent>() {

            
            public void addHandler(final Handler<SelectionChangedEvent> handler) {
            	grid.getSelectionModel().getListeners(Events.SelectionChange).clear();
                grid.getSelectionModel().addListener(Events.SelectionChange, new Listener<SelectionChangedEvent>() {

                    
                    public void handleEvent(SelectionChangedEvent ge) {
                        handler.onSelected(ge);
                    }
                });
            }
        };
    }

    public static HasSelected<GridEvent> createHasSelected(final Grid grid) {
                return new HasSelected<GridEvent>() {

            
            public void addHandler(final Handler<GridEvent> handler) {
            	grid.getListeners(Events.RowClick).clear();
                grid.addListener(Events.RowClick, new Listener<GridEvent>() {

                    
                    public void handleEvent(GridEvent ge) {
                        handler.onSelected(ge);
                    }
                });
            }
        };

    }

    public static HasSelected<GridEvent> createDoubleClick(final Grid grid) {
                return new HasSelected<GridEvent>() {

            
            public void addHandler(final Handler<GridEvent> handler) {
            	grid.getListeners(Events.RowDoubleClick).clear();
                grid.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {

                    
                    public void handleEvent(GridEvent ge) {
                        handler.onSelected(ge);
                    }
                });
            }
        };

    }

    public static HasSelected createHasSelected(final Component component) {
        return createHasSelected(component, Events.OnClick);
    }

    public static HasSelected createHasSelected(final Component component, final EventType type) {
        return new HasSelected() {

            
            public void addHandler(final Handler handler) {
            	component.getListeners(type).clear();
                component.addListener(type, new Listener<ComponentEvent>() {

                    
                    public void handleEvent(ComponentEvent be) {
                        handler.onSelected(be);
                    }
                });
            }
        };
    }

    public static HasDoubleClicked createHasDoubleClicked(final Component component) {
        return new HasDoubleClicked() {

            
            public void addHandler(final Handler handler) {
            	component.getListeners(Events.OnDoubleClick).clear();
                component.addListener(Events.OnDoubleClick, new Listener<TreePanelEvent<TreeModel>>() {

                    
                    public void handleEvent(TreePanelEvent<TreeModel> event) {
                        handler.onDoubleClicked();
                    }
                });
            }
        };
    }

    public static HasDoubleClicked createHasDoubleClickedGridRow(final Component component) {
        return new HasDoubleClicked() {
            
            public void addHandler(final Handler handler) {
            	component.getListeners(Events.RowDoubleClick).clear();
                component.addListener(Events.RowDoubleClick, new Listener<GridEvent>() {

                    
                    public void handleEvent(GridEvent be) {
                        handler.onDoubleClicked();
                    }
                });
            }
        };
    }

    public static HasValue<String> createHasValue(final ComboBox comboBox, final String property) {
        return new HasValue<String>() {

            
            public String getValue() {
                return (String) (comboBox.getValue() == null ? null : comboBox.getValue().get(property));
            }

            
            public void setValue(String arg0) {
                throw new IllegalStateException("not implemented yet");
            }
        };
    }

    public static HasValueChanged createHasValueChanged(final ComboBox comboBox) {
        return createHasValueChanged((SelectionProvider) comboBox);
    }


    public static HasValueChanged createHasValueChanged(final CheckBox comboBox) {
        return createHasValueChangedForField(comboBox);
    }

    public static HasValueChanged createHasValueChanged(final SelectionProvider selectionProvider) {
        return new HasValueChanged() {

            
            public void addHandler(final Handler handler) {
            	selectionProvider.addSelectionChangedListener(new SelectionChangedListener() {

                    
                    public void selectionChanged(SelectionChangedEvent se) {
                        handler.onValueChange();
                    }
                });
            }

            
            public void addHandler(final HandlerWithEvent handler) {
                selectionProvider.addSelectionChangedListener(new SelectionChangedListener() {

                    
                    public void selectionChanged(SelectionChangedEvent se) {
                        handler.onValueChange(se.getSelectedItem());
                    }
                });
            }

        };
    }

    private static HasValueChanged createHasValueChangedForField(final Field field) {
        return new HasValueChanged() {

                    
                    public void addHandler(final Handler handler) {

                        field.addKeyListener(new KeyListener() {

                            
                            public void componentKeyDown(ComponentEvent event) {
                                handler.onValueChange();
                                super.componentKeyDown(event);
                            }

                            
                            public void componentKeyPress(ComponentEvent event) {
                                handler.onValueChange();
                                super.componentKeyPress(event);
                            }

                            
                            public void componentKeyUp(ComponentEvent event) {
                                handler.onValueChange();
                                super.componentKeyUp(event);
                            }
                        });
                    }

                    
                    public void addHandler(HandlerWithEvent h) {
                        throw new UnsupportedOperationException("not implemented");
                    }
                };

    }

    public static HasValueChanged createHasValueChanged(final TextField textField) {
        return createHasValueChangedForField(textField);
    }

    public static HasValueChanged createHasValueChanged(final DateField dateField) {
        return new HasValueChanged() {
            
            public void addHandler(final Handler handler) {
                dateField.addListener(Events.Valid, new Listener<FieldEvent>() {
                    
                    public void handleEvent(FieldEvent be) {
                        handler.onValueChange();
                    }
                });
            }

            
            public void addHandler(HandlerWithEvent h) {
                throw new UnsupportedOperationException("not implemented");
            }
        };
    }

    public static HasValueChanged createHasFinishedEdit(final EditorGrid<ModelData> table) {
        return new HasValueChanged() {

            
            public void addHandler(final Handler h) {
                table.addListener(Events.AfterEdit, new Listener<BaseEvent>() {
                    
                    public void handleEvent(BaseEvent be) {
                        h.onValueChange();
                    }
                });
            }

            
            public void addHandler(HandlerWithEvent h) {
                throw new UnsupportedOperationException("not implemented");
            }
        };
    }

    public static HasFilterChanged createHasFilterChanged(final ListStore<ModelData> store) {
    return new HasFilterChanged() {

            @Override
            public void addHandler( final Handler h ) {
                store.addListener( Store.Filter, new Listener<StoreEvent>() {

                    @Override
                    public void handleEvent( StoreEvent se ) {
                        h.onFilterChange( se );
                    }
                } );
            }
        };
    }
}
