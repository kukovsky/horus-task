import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileCabinetImplTest {

    @Test
    void thatFolderCanBeFoundCorrectlyByName() {
        // given
        // Create a list of folders
        List<Folder> folders = new ArrayList<>();
        folders.add(new FolderImpl("Folder1", "SMALL"));
        folders.add(new FolderImpl("Folder2", "MEDIUM"));
        folders.add(new FolderImpl("Folder3", "SMALL"));
        folders.add(new FolderImpl("Folder4", "SMALL"));
        folders.add(new MultiFolderImpl("MultiFolder1", "MEDIUM", List.of(new FolderImpl("Folder6", "LARGE"))));
        folders.add(new MultiFolderImpl("MultiFolder2", "LARGE", List.of(new FolderImpl("Folder8", "SMALL"))));

        // Create a list of multiFolders
        List<Folder> multiFolder = new ArrayList<>();
        multiFolder.add(new MultiFolderImpl("MultiFolder1", "SMALL", folders));

        // Create a FileCabinetImpl object
        FileCabinetImpl fileCabinet1 = new FileCabinetImpl(folders);
        FileCabinetImpl fileCabinet2 = new FileCabinetImpl(multiFolder);

        // when
        // Find the folder by name
        Optional<Folder> folder1 = fileCabinet1.findFolderByName("Folder1");
        Optional<Folder> folder2 = fileCabinet2.findFolderByName("MultiFolder1");

        // then
        // Check if the folder is present
        assertTrue(folder1.isPresent());
        // Check if the folder name is correct
        assertEquals("Folder1", folder1.get().getName());
        assertTrue(folder2.isPresent());
        assertEquals("MultiFolder1", folder2.get().getName());

    }

    @Test
    void thatFolderCanBeFoundBySize() {
        // given
        // Create a list of folders
        List<Folder> folders = new ArrayList<>();
        folders.add(new FolderImpl("Folder1", "SMALL"));
        folders.add(new FolderImpl("Folder2", "MEDIUM"));
        folders.add(new FolderImpl("Folder3", "LARGE"));
        folders.add(new FolderImpl("Folder4", "SMALL"));
        folders.add(new MultiFolderImpl("Folder5", "MEDIUM", List.of(new FolderImpl("Folder6", "LARGE"))));
        folders.add(new MultiFolderImpl("Folder7", "LARGE", List.of(new FolderImpl("Folder8", "SMALL"))));

        // when
        // Create a FileCabinetImpl object
        FileCabinetImpl fileCabinet = new FileCabinetImpl(folders);
        // Find the folders by size
        List<Folder> findFolders = fileCabinet.findFoldersBySize("SMALL");

        // then
        // Check if the size of the folders is correct
        assertEquals(3, findFolders.size());
        // Check if the size name of the folders is correct
        assertEquals("SMALL", findFolders.get(0).getSize());
        assertEquals("SMALL", findFolders.get(1).getSize());
        assertEquals("SMALL", findFolders.get(2).getSize());
    }

    @Test
    void countAllObjectsFromStructure() {
        // given
        // Create a list of folders
        List<Folder> folders = new ArrayList<>();
        folders.add(new FolderImpl("Folder1", "SMALL"));
        folders.add(new FolderImpl("Folder2", "MEDIUM"));
        folders.add(new FolderImpl("Folder3", "LARGE"));
        folders.add(new FolderImpl("Folder4", "SMALL"));
        folders.add(new MultiFolderImpl("Folder5", "MEDIUM", List.of(new FolderImpl("Folder6", "LARGE"))));
        folders.add(new MultiFolderImpl("Folder7", "LARGE", List.of(new FolderImpl("Folder8", "SMALL"))));
        // when
        // Create a FileCabinetImpl object
        FileCabinetImpl fileCabinet = new FileCabinetImpl(folders);
        // Count the number of folders
        int count = fileCabinet.count();

        // then
        // Check if the count is correct
        assertEquals(8, count);

    }
}