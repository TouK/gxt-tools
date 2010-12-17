package pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp;

import pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.handlers.HasValueChanged;
import pl.touk.wonderfulsecurity.gwt.client.ui.infrastructure.mvp.ViewMemberFactory;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionProvider;

@RunWith(MockitoJUnitRunner.class)
public class ViewMemberFactoryTest {


    @Test
    public void testCreateHasValueChanged() {

        SelectionProvider sp = mock(SelectionProvider.class);

        HasValueChanged createHasValueChanged = ViewMemberFactory.createHasValueChanged(sp);
        createHasValueChanged.addHandler(new HasValueChanged.Handler() {

            @Override
            public void onValueChange() {
            }
        });

        verify(sp).addSelectionChangedListener(any(SelectionChangedListener.class));
    }

}
