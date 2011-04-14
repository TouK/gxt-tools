package pl.touk.tola.gwt.client.widgets;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.ColorPaletteEvent;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.ColorPalette;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.AdapterField;

/**
 * Bindable color picker form field.
 *
 * @author Tomasz Przybysz <tpr@touk.pl>
 *
 * @see com.extjs.gxt.ui.client.widget.form.AdapterField
 * @see com.extjs.gxt.ui.client.widget.ColorPalette
 * @see com.extjs.gxt.ui.client.widget.menu.ColorMenu
 */
public class ColorPickerField extends AdapterField {

    public static String DEFAULT_LABEL = "Kolor";
    final ColorMenu colorMenu;
    final TextField colorView;
    final Button selectButton;

    /**
     * Creates field with default colors palette.
     */
    public ColorPickerField() {
        super( new HorizontalPanel() );

        setFieldLabel( DEFAULT_LABEL );
        setFireChangeEventOnSetValue( true );

        colorMenu = createColorMenu();
        colorView = createColorView();
        selectButton = createSelectButton();

        addComponents();

        addListener( Events.Change, createFieldChangeListener() );
    }

    /**
     * Creates field with custom colors palette.
     *
     * @param colors colors values in hex format without leading '#'
     */
    public ColorPickerField( String... colors ) {
        this();
        colorMenu.getColorPalette().setColors( colors );
    }

    /**
     * Returns color in hex format.
     *
     * @return color hex value without leading '#'
     */
    @Override
    public String getValue() {
        return (String) super.getValue();
    }

    /**
     * Sets color in hex format.
     *
     * @param colorHex color hex value without leading '#'
     * @throws IllegalArgumentException if color is not defined in field's palette
     */
    public void setValue( String colorHex ) {
        colorHex = colorHex.toUpperCase();

        if (!isColorInPalette( colorHex )) {
            throw new IllegalArgumentException( "Color [" + colorHex + "] not in palette: " );
        }

        super.setValue( colorHex );
    }

    /**
     * Alias of {@link #getValue()}.
     */
    public String getColor() {
        return getValue();
    }

    /**
     * Alias of {@link #setValue(java.lang.String)}.
     */
    public void setColor( String colorHex ) {
        setValue( colorHex );
    }

    /**
     * Checks whether color is defined in field's palette.
     *
     * @param colorHex hex color value without leading '#'
     * @return true if color in palette, false otherwise
     */
    public boolean isColorInPalette( String colorHex ) {
        String[] colors = colorMenu.getColorPalette().getColors();
        for (String paletteColor : colors) {
            if (paletteColor.equalsIgnoreCase( colorHex )) {
                return true;
            }
        }
        return false;
    }

    private void addComponents() {
        HorizontalPanel container = (HorizontalPanel) this.getWidget();
        container.add( colorView );
        container.add( new Label( "&nbsp&nbsp" ) );
        container.add( selectButton );
    }

    private TextField<String> createColorView() {
        TextField<String> field = new TextField<String>() {

            @Override
            public void setValue( String value ) {
                input.setStyleAttribute( "background", '#' + value );
            }
        };

        field.setWidth( 25 );
        field.setReadOnly( true );

        return field;
    }

    private ColorMenu createColorMenu() {
        ColorMenu menu = new ColorMenu();

        menu.getColorPalette().addListener( Events.Select, createColorPaletteSelectionListener() );

        return menu;
    }

    private Listener createFieldChangeListener() {
        return new Listener<FieldEvent>() {

            @Override
            public void handleEvent( FieldEvent fe ) {
                String color = String.valueOf( fe.getValue() );
                colorMenu.getColorPalette().setValue( color );
                colorView.setValue( color );
            }
        };
    }

    private SelectionListener createColorPaletteSelectionListener() {
        return new SelectionListener<ColorPaletteEvent>() {

            @Override
            public void componentSelected( ColorPaletteEvent ce ) {
                String color = ce.getColor();
                ColorPickerField.this.setValue( color );
            }
        };
    }

    private Button createSelectButton() {
        Button btn = new Button( "wybierz" );
        btn.setMenu( colorMenu );

        return btn;
    }
}

/**
 * ColorPalette bug fix for GXT version 2.1.1 (and following I suppose).
 *
 * Bug occurs when setValue(String) method is used after previously selecting color
 * with mouse on ColorPalette. Problem is the color selected with mouse doesn't
 * deselect visually (still looks like "clicked" despite ColorPalette value changed).
 *
 * @author Tomasz Przybysz <tpr@touk.pl>
 *
 * @see com.extjs.gxt.ui.client.widget.ColorPalette
 */
class ColorMenu extends com.extjs.gxt.ui.client.widget.menu.ColorMenu {

    public ColorMenu() {
        super();
        palette = createPatchedPalette();
        removeAll();
        add(palette);
    }

    private ColorPalette createPatchedPalette() {
        return new ColorPalette() {
            @Override
            public void setValue( String value ) {
                if (value == null || value.length() != 6) { // String with value "null" gets in here out of nowhere o_O
                    return;
                }

                if (rendered) {
                    select( value );
                } else {
                    super.setValue( value );
                }
            }
        };
    }
}
