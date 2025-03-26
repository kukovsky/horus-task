import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileCabinetImplTest {

    @Test
    void findFolderByName() {
        // given
        List<Folder> folders = new ArrayList<>();
        folders.add(new FolderImpl("Folder1", "SMALL"));
        folders.add(new FolderImpl("Folder2", "MEDIUM"));
        folders.add(new FolderImpl("Folder3", "SMALL"));
        folders.add(new FolderImpl("Folder4", "SMALL"));
        folders.add(new MultiFolderImpl("MultiFolder1", "MEDIUM", List.of(new FolderImpl("Folder6", "LARGE"))));
        folders.add(new MultiFolderImpl("MultiFolder2", "LARGE", List.of(new FolderImpl("Folder8", "SMALL"))));

        List<Folder> multiFolder = new ArrayList<>();
        multiFolder.add(new MultiFolderImpl("MultiFolder1", "SMALL", folders));

        FileCabinetImpl fileCabinet1 = new FileCabinetImpl(folders);
        FileCabinetImpl fileCabinet2 = new FileCabinetImpl(multiFolder);

        // when
        Optional<Folder> folder1 = fileCabinet1.findFolderByName("Folder1");
        Optional<Folder> folder2 = fileCabinet2.findFolderByName("MultiFolder1");

        // then
        assertTrue(folder1.isPresent());
        assertEquals("Folder1", folder1.get().getName());
        assertTrue(folder2.isPresent());
        assertEquals("MultiFolder1", folder2.get().getName());

    }

    @Test
    void findFoldersBySize() {
        // given
        List<Folder> folders = new ArrayList<>();
        folders.add(new FolderImpl("Folder1", "SMALL"));
        folders.add(new FolderImpl("Folder2", "MEDIUM"));
        folders.add(new FolderImpl("Folder3", "LARGE"));
        folders.add(new FolderImpl("Folder4", "SMALL"));
        folders.add(new MultiFolderImpl("Folder5", "MEDIUM", List.of(new FolderImpl("Folder6", "LARGE"))));
        folders.add(new MultiFolderImpl("Folder7", "LARGE", List.of(new FolderImpl("Folder8", "SMALL"))));
        // when
        FileCabinetImpl fileCabinet = new FileCabinetImpl(folders);
        List<Folder> findFolders = fileCabinet.findFoldersBySize("SMALL");

        // then
        assertEquals(3, findFolders.size());
        System.out.println(findFolders);
        assertEquals("SMALL", findFolders.get(0).getSize());
        assertEquals("SMALL", findFolders.get(1).getSize());
        assertEquals("SMALL", findFolders.get(2).getSize());
    }

    @Test
    void count() {
        // given
        List<Folder> folders = new ArrayList<>();
        folders.add(new FolderImpl("Folder1", "SMALL"));
        folders.add(new FolderImpl("Folder2", "MEDIUM"));
        folders.add(new FolderImpl("Folder3", "LARGE"));
        folders.add(new FolderImpl("Folder4", "SMALL"));
        folders.add(new MultiFolderImpl("Folder5", "MEDIUM", List.of(new FolderImpl("Folder6", "LARGE"))));
        folders.add(new MultiFolderImpl("Folder7", "LARGE", List.of(new FolderImpl("Folder8", "SMALL"))));
        // when
        FileCabinetImpl fileCabinet = new FileCabinetImpl(folders);
        int count = fileCabinet.count();

        // then
        assertEquals(8, count);

    }
}