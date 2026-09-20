import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("Mixed adds preserve logical order and size")
    public void mixedAddGetAndSizeTest() {
        // Arrange: start from a known empty deque.
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();

        // Act: use both ends so the test checks logical order, not physical indices.
        deque.addLast(10);   // [10]
        deque.addLast(20);   // [10, 20]
        deque.addFirst(5);   // [5, 10, 20]

        // Assert: check every observable result affected by the operations.
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.isEmpty()).isFalse();
        assertThat(deque.get(0)).isEqualTo(5);
        assertThat(deque.get(1)).isEqualTo(10);
        assertThat(deque.get(2)).isEqualTo(20);
        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(3)).isNull();
    }

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

}
