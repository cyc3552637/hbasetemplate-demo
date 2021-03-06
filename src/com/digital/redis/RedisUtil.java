package com.digital.redis;
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
  
import org.springframework.cache.Cache;  
import org.springframework.cache.support.SimpleValueWrapper;  
import org.springframework.dao.DataAccessException;  
import org.springframework.data.redis.connection.RedisConnection;  
import org.springframework.data.redis.core.RedisCallback;  
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil implements Cache{
    
    private RedisTemplate<String, Object> redisTemplate;    
    private String name;    
    public RedisTemplate<String, Object> getRedisTemplate() {  
        return redisTemplate;    
    }  
       
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;    
    }  
       
    public void setName(String name) {  
        this.name = name;    
    }  
       
    public String getName() {  
        return this.name;    
    }  
  
    public Object getNativeCache() {  
        return this.redisTemplate;    
    }  
    
    /**
     * 从缓存中获取key
     */
    public ValueWrapper get(Object key) {  
     System.out.println("get key");  
      final String keyf =  key.toString();  
      Object object = null;  
      object = redisTemplate.execute(new RedisCallback<Object>() {  
      public Object doInRedis(RedisConnection connection)    
                  throws DataAccessException {  
          byte[] key = keyf.getBytes();  
          byte[] value = connection.get(key);  
          if (value == null) {  
             return null;  
            }  
          return toObject(value);  
          }  
       });  
        return (object != null ? new SimpleValueWrapper(object) : null);  
      }  
    
     /**
      * 将一个新的key保存到缓存中
      * 先拿到需要缓存key名称和对象，然后将其转成ByteArray
      */
     public void put(Object key, Object value) {  
     System.out.println("put key");  
       final String keyf = key.toString();    
       final Object valuef = value;    
       final long liveTime = 86400;    
       redisTemplate.execute(new RedisCallback<Long>() {    
           public Long doInRedis(RedisConnection connection)    
                   throws DataAccessException {    
                byte[] keyb = keyf.getBytes();    
                byte[] valueb = toByteArray(valuef);    
                connection.set(keyb, valueb);    
                if (liveTime > 0) {    
                    connection.expire(keyb, liveTime);    
                 }    
                return 1L;    
             }    
         });    
      }  
  
      private byte[] toByteArray(Object obj) {    
         byte[] bytes = null;    
         ByteArrayOutputStream bos = new ByteArrayOutputStream();    
         try {    
           ObjectOutputStream oos = new ObjectOutputStream(bos);    
           oos.writeObject(obj);    
           oos.flush();    
           bytes = bos.toByteArray();    
           oos.close();    
           bos.close();    
          }catch (IOException ex) {    
               ex.printStackTrace();    
          }    
          return bytes;    
        }    
  
       private Object toObject(byte[] bytes) {  
         Object obj = null;    
           try {  
               ByteArrayInputStream bis = new ByteArrayInputStream(bytes);    
               ObjectInputStream ois = new ObjectInputStream(bis);    
               obj = ois.readObject();    
               ois.close();    
               bis.close();    
           } catch (IOException ex) {    
               ex.printStackTrace();    
            } catch (ClassNotFoundException ex) {    
               ex.printStackTrace();    
            }    
            return obj;    
        }  
       
       /**
        * 删除key
        */
       public void evict(Object key) {    
        System.out.println("del key");  
         final String keyf = key.toString();    
         redisTemplate.execute(new RedisCallback<Long>() {    
         public Long doInRedis(RedisConnection connection)    
                   throws DataAccessException {    
             return connection.del(keyf.getBytes());    
            }    
          });    
        }  
       
        /**
         * 清空key
         */
        public void clear() {    
         System.out.println("clear key");  
           redisTemplate.execute(new RedisCallback<String>() {    
                public String doInRedis(RedisConnection connection)    
                        throws DataAccessException {    
                  connection.flushDb();    
                    return "ok";    
               }    
           });    
        }  
  
        public <T> T get(Object key, Class<T> type) {  
            return null;  
        }  
      
        public ValueWrapper putIfAbsent(Object key, Object value) {  
            return null;  
        }  
      
}