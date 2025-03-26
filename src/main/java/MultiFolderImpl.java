import java.util.List;

public record MultiFolderImpl(String getName, String getSize, List<Folder> getFolders) implements MultiFolder {
}
