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
        var findFolder = getAllFolders(folders).stream()
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
        if (findFolder.isEmpty()) {
            throw new NotFoundException("Folder with name: " + name + " not found");
        }
        return findFolder;
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        var findFolders = getAllFolders(folders).stream()
                .filter(findFolder -> findFolder.getSize().equals(size))
                .toList();
        if (findFolders.isEmpty()) {
            throw new NotFoundException("Folders with size: " + size + " not found");
        }
        return findFolders;
    }

    @Override
    public int count() {
        return getAllFolders(folders).size();
    }

    private List<Folder> getAllFolders(List<Folder> folders) {
        return folders.stream()
                .flatMap(folder -> {
            if (folder instanceof MultiFolder) {
                return Stream.concat(Stream.of(folder), getAllFolders(((MultiFolder) folder).getFolders()).stream());
            } else {
                return Stream.of(folder);
            }
        })
                .toList();
    }

}
