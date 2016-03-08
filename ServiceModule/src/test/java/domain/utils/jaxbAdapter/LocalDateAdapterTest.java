package domain.utils.jaxbAdapter;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import org.pode.cosmos.domain.utils.jaxbAdapter.LocalDateAdapter;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by patrick on 08.03.16.
 */
public class LocalDateAdapterTest {

    @Test
    public void unmarschal_isoDate_validLocalDate() throws Exception{
        String isoDate = "2012-12-12";
        LocalDateAdapter adapter = new LocalDateAdapter();

        LocalDate localDate = adapter.unmarshal(isoDate);

        assertThat(localDate, equalTo(LocalDate.of(2012,12,12)));
    }

    @Test
    public void unmarschal_isoBasicDate_validLocalDate() throws Exception{
        String isoDate = "20121212";
        LocalDateAdapter adapter = new LocalDateAdapter();

        LocalDate localDate = adapter.unmarshal(isoDate);

        assertThat(localDate, equalTo(LocalDate.of(2012,12,12)));
    }

    @Test
    public void unmarschal_slashPatternDate_validLocalDate() throws Exception{
        String isoDate = "2012/12/12";
        LocalDateAdapter adapter = new LocalDateAdapter();

        LocalDate localDate = adapter.unmarshal(isoDate);

        assertThat(localDate, equalTo(LocalDate.of(2012,12,12)));
    }

    @Test
    public void unmarschal_isoDateTime_validLocalDate() throws Exception{
        String isoDate = "2012-12-12T02:02:12.012+01:00";
        LocalDateAdapter adapter = new LocalDateAdapter();

        LocalDate localDate = adapter.unmarshal(isoDate);

        assertThat(localDate, equalTo(LocalDate.of(2012,12,12)));
    }

    @Test(expected = IllegalStateException.class)
    public void unmarschal_notSupportedFormat_IllegalStateException() throws Exception{
        String isoDate = "2012:12:12";
        LocalDateAdapter adapter = new LocalDateAdapter();

        adapter.unmarshal(isoDate);
    }

}