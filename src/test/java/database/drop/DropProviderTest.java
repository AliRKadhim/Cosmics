package database.drop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.life.MonsterDropEntry;
import server.life.MonsterGlobalDropEntry;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class DropProviderTest {

    @Mock
    private DropDao dropDao;

    private DropProvider dropProvider;

    @BeforeEach
    void reset() {
        MockitoAnnotations.openMocks(this);
        this.dropProvider = new DropProvider(dropDao);
    }

    @Test
    void getMonsterDropEntries_noDrops() {
        when(dropDao.getMonsterDrops(anyInt())).thenReturn(Collections.emptyList());

        List<MonsterDropEntry> dropEntries = dropProvider.getMonsterDropEntries(489340);

        assertTrue(dropEntries.isEmpty());
    }

    @Test
    void getMonsterDropEntries() {
        MonsterDrop snailShellDrop = snailShellDrop();
        when(dropDao.getMonsterDrops(anyInt())).thenReturn(List.of(snailShellDrop));

        List<MonsterDropEntry> dropEntries = dropProvider.getMonsterDropEntries(100100);

        assertEquals(1, dropEntries.size());
        MonsterDropEntry dropEntry = dropEntries.get(0);
        assertEquals(snailShellDrop.itemId(), dropEntry.itemId);
        assertEquals(snailShellDrop.minQuantity(), dropEntry.Minimum);
        assertEquals(snailShellDrop.maxQuantity(), dropEntry.Maximum);
        assertEquals(snailShellDrop.chance(), dropEntry.chance);
        assertEquals(0, dropEntry.questid);
    }

    @Test
    void getCachedMonsterDropEntries() {
        when(dropDao.getMonsterDrops(anyInt())).thenReturn(List.of(snailShellDrop()));
        int monsterId = 100100;

        List<MonsterDropEntry> dropEntries1 = dropProvider.getMonsterDropEntries(monsterId);
        List<MonsterDropEntry> dropEntries2 = dropProvider.getMonsterDropEntries(monsterId);

        assertEquals(1, dropEntries1.size());
        assertEquals(1, dropEntries2.size());
        MonsterDropEntry dropEntry1 = dropEntries1.get(0);
        MonsterDropEntry dropEntry2 = dropEntries2.get(0);
        assertEquals(dropEntry1.itemId, dropEntry2.itemId);
        assertEquals(dropEntry1.Minimum, dropEntry2.Minimum);
        assertEquals(dropEntry1.Maximum, dropEntry2.Maximum);
        assertEquals(dropEntry1.questid, dropEntry2.questid);
        assertEquals(dropEntry1.chance, dropEntry2.chance);
        verify(dropDao, times(1)).getMonsterDrops(anyInt());
    }

    private MonsterDrop snailShellDrop() {
        return new MonsterDrop(100100, 4000019, 1, 2, null, 600_000);
    }

    @Test
    void getCachedGlobalDropEntries() {
        GlobalMonsterDrop globalDrop = new GlobalMonsterDrop(2049100, -1, 2, 3, null, 450);
        when(dropDao.getGlobalMonsterDrops()).thenReturn(List.of(globalDrop));

        List<MonsterGlobalDropEntry> dropEntries1 = dropProvider.getGlobalDropEntries();
        List<MonsterGlobalDropEntry> dropEntries2 = dropProvider.getGlobalDropEntries();

        assertEquals(1, dropEntries1.size());
        assertEquals(1, dropEntries2.size());
        MonsterGlobalDropEntry dropEntry1 = dropEntries1.get(0);
        MonsterGlobalDropEntry dropEntry2 = dropEntries2.get(0);
        assertEquals(2049100, dropEntry1.itemId);
        assertEquals(dropEntry1.itemId, dropEntry2.itemId);
        assertEquals(-1, dropEntry1.continentid);
        assertEquals(dropEntry1.continentid, dropEntry2.continentid);
        assertEquals(2, dropEntry1.Minimum);
        assertEquals(dropEntry1.Minimum, dropEntry2.Minimum);
        assertEquals(3, dropEntry1.Maximum);
        assertEquals(dropEntry1.Maximum, dropEntry2.Maximum);
        assertEquals(0, dropEntry1.questid);
        assertEquals(dropEntry1.questid, dropEntry2.questid);
        assertEquals(450, dropEntry1.chance);
        assertEquals(dropEntry1.chance, dropEntry2.chance);
        verify(dropDao, times(1)).getGlobalMonsterDrops();
    }

    // TODO: add tests for getRandomStealDrop() once ItemInformationProvider is able to be mocked.
    // Currently, it does database calls (and a bunch of other stuff) in the constructor, which is problematic.
}