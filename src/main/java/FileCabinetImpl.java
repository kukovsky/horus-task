import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinetImpl implements Cabinet {
    private final List<Folder> folders;

    public FileCabinetImpl(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        var findFolder = getAllFolders(folders)
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
        if (findFolder.isEmpty()) {
            System.out.println("Folder not found");
        }
        return findFolder;
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        var findFolders = getAllFolders(folders)
                .filter(findFolder -> findFolder.getSize().equals(size))
                .toList();
        if (findFolders.isEmpty()) {
            System.out.println("Folders not found");
        }
        return findFolders;
    }

    @Override
    public int count() {
        return (int) getAllFolders(folders).count();
    }

    private Stream<Folder> getAllFolders(List<Folder> folders) {
        return folders.stream().flatMap(folder -> {
            if (folder instanceof MultiFolder) {
                return Stream.concat(Stream.of(folder), getAllFolders(((MultiFolder) folder).getFolders()));
            } else {
                return Stream.of(folder);
            }
        });
    }

}
