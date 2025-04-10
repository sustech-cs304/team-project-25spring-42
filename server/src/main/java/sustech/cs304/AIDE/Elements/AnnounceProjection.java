package sustech.cs304.AIDE.Elements;
import java.time.LocalDateTime;
public interface AnnounceProjection {
    Long getId();
    String getCourseId();
    String getAnnounceName();
    LocalDateTime getUpLoadTime();
    boolean isVisible(); 
}
