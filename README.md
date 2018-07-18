[![Project Status: Active - Initial development has started, temporary release; work hasn't been stopped ](http://www.repostatus.org/badges/0.1.0/active.svg)](http://www.repostatus.org/#active)

FireBaseVenom
=============
A Firebase Wrapper-Library made to simplify mobile-backend programming on android
with inclusive time and date formating options.

## Quick Start

> Gradle

```xml
   dependencies {
        compile 'com.github.54LiNKeR:FireBaseVenom:1.0.0'
    }
```

> JAVA

- *public constants*

```java
       public static String TAG;
       public static final boolean NO_TIMESTAMP;
       public static final boolean YES_TIMESTAMP;
       public static final boolean NO_PUSH;
       public static final boolean YES_PUSH;
       public static final String[] DATE_FORMAT_STRINGS
```

- *public interfaces*

```java
         public interface venomSize
         public interface venomStingFangs
         public interface venomHashResults
         public interface venomListResults
         public interface venomDelete
         public interface venomUploadDownload
         public interface venomPushCallBack
         public interface venomPullCallBack
         public interface Time
```

- *public methods*

```java
           public static void checkIfchildExists(DatabaseReference DbRf, final String childname, final venomStingFangs djI)
           public static  <T extends Object> void checkIfchildExistsByProperty(DatabaseReference DbRf, String Child , String PropertyChild, T PropertyVal, venomHashResults dsR)
           public static void SizeOfChildren(DatabaseReference DbRf, final String childname, final venomSize djI)
           public static <T extends Object> Query SearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, venomHashResults dsR)
           public static <T extends Object> Query SpecificSearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, String PropertyKey, venomHashResults dsR)
           public static <T extends Object> Query ltdKidSearch(DatabaseReference DbRf, String Child, String PropertyChild, int limit, boolean firstlimit, venomHashResults dsR)
           public static <T extends Object> Query ltdKidSearchByKey(DatabaseReference DbRf, String Child, int limit, boolean firstlimit, venomHashResults dsR)
           public static  <T extends Object>Query ltdSearchByProperty(DatabaseReference DbRf, String Child, String PropertyChild, T PropertyVal, int limit, boolean firstlimit, final venomHashResults dsR)
           public static <T extends Object>Query SearchAt(Query query, String PropertyChild, String How, T startPropertyVal, T endPropertyVal, final venomHashResults dsR)
           public static  Query getLastKid(DatabaseReference DbRf, String Child, String PropertyChild, boolean orderByKey, venomHashResults dsR)
           public static  void getMostCurrentTimeStamp(DatabaseReference DbRf, String Child, final Time Tstp)
           public static void addChildListerner(DatabaseReference DbRf, String Child, ChildEventListener childEventListener)
           public static Query Search(Query query,final venomHashResults dsR)
           public static Query downloadNode(DatabaseReference DbRf, String Child, final venomHashResults dsR)
           public static Query downloadKeys(DatabaseReference DbRf, String Child, final venomListResults dsR)
           public static void deleteNode(DatabaseReference DbRf,String NodeChild)
           private static void deleteNodes(Query query,final venomDelete dsD)
           public static UploadTask UploadFile(StorageReference Strf, File f, boolean viaInputStream, final venomUploadDownload dsPD) throws FileNotFoundException
           public static<T extends Object> FileDownloadTask DownloadFile(T strf, String downloadPath, final venomUploadDownload dsPD) throws IOException
           public static<T extends Object> void push(DatabaseReference DbRf, String Child, T Value, boolean push, final venomPushCallBack Icb)
           public static void push(DatabaseReference DbRf, String Child, boolean timestamp, boolean push, final venomPushCallBack Icb, ArrayList<String> Keys, ArrayList<String> Objects)
           public static void push(DatabaseReference DbRf, String Child, boolean timestamp, boolean push, HashMap<String,Object> rawHash, final venomPushCallBack Icb)
           public static void pull(DatabaseReference DbRf, String Child, final venomPullCallBack pullover)
           public static HashMap<String,Object> Kiddup(ArrayList<String> Keys, ArrayList<String> Objects)
           public static HashMap<String,Object> JSONtoHashMap(Object JSON)throws JSONException
           public static JSONObject HashMaptoJSON(HashMap<String,Object> map)
           public static Object[] LT2UTC_datetime() throws ParseException
           public static Object[] UTC2LT_datetime(long rawUTC) throws ParseException
           public static long Date2Long(Date date)
           public static String FormatDate(Date date, String FormatString)
           public static Date getCurrentDate()
           public static Date String2Date(String date, String Frmt) throws ParseException
           public static Date Long2Date(long date)
           public static String getTimeZoneAbbrev()
           public static int getCurrentHour()
           public static int getCurrentMinute()
           public static int getCurrentSecond()
           public static Locale getCurrentLocale(Context context)
```

> project is still under dev