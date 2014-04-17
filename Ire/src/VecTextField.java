import java.io.Reader;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

public class VecTextField extends Field {
	public static final FieldType TYPE_NOT_STORED = new FieldType();
	public static final FieldType TYPE_STORED = new FieldType();

	static {
		TYPE_NOT_STORED.setIndexed(true);
		TYPE_NOT_STORED.setTokenized(true);
		TYPE_NOT_STORED.setStoreTermVectors(true);
		TYPE_NOT_STORED.setStoreTermVectorPositions(true);
		TYPE_NOT_STORED.freeze();

		TYPE_STORED.setIndexed(true);
		TYPE_STORED.setTokenized(true);
		TYPE_STORED.setStored(true);
		TYPE_STORED.setStoreTermVectors(true);
		TYPE_STORED.setStoreTermVectorPositions(true);
		TYPE_STORED.freeze();
	}

	public VecTextField(String name, Reader reader, Store store) {
		super(name, reader, store == Store.YES ? TYPE_STORED : TYPE_NOT_STORED);
	}

	public VecTextField(String name, String value, Store store) {
		super(name, value, store == Store.YES ? TYPE_STORED : TYPE_NOT_STORED);
	}

	public VecTextField(String name, TokenStream stream) {
		super(name, stream, TYPE_NOT_STORED);
	}
}