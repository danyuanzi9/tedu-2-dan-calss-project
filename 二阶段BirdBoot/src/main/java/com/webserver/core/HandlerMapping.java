package com.webserver.core;

import com.webserver.annotations.Controller;
import com.webserver.annotations.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
/**
 * 该类用于维护请求路径与业务处理方法的对应关系
 */
public class HandlerMapping {
    /**
     * key:请求路径
     * value:处理该请求的方法
     * 例如:
     *  key->/regUser
     *  value->Method对象，表示UserController中的reg方法
     */

    private static Map<String, Method> mapping = new HashMap<>();

    static {
        initMapping();
    }

    private static void initMapping(){
        try {
            //定位启动当前Boot的启动类所在的目录
            File rootDir = new File(
                    BirdBootApplication.primarySource.getResource(".").toURI()
            );
            //定位controller包
            File dir = new File(rootDir,"controller");
            if (dir.exists()){//确保controller目录存在
                File[] subs = dir.listFiles(f->f.getName().endsWith(".class"));
                for (File sub : subs){
                    String className = sub.getName().replace(".class","");
                    //启动类所在的包名
                    String packageName = BirdBootApplication.primarySource.getPackage().getName();

                    Class cls = Class.forName(packageName+".controller."+className);
                    if (cls.isAnnotationPresent(Controller.class)){//判断该类是否被注解@Controller标注
                        Method[] methods = cls.getDeclaredMethods();
                        for (Method method : methods){
                            if (method.isAnnotationPresent(RequestMapping.class)){//判断方法是否被注解@RequestMapping标注
                                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                                String value = rm.value();//获取@RequestMapping上的参数，该参数记录这该方法处理的请求路径
                                mapping.put(value,method);
                            }
                        }
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据请求路径过去对应的业务处理方法
     * @param uri
     * @return
     */
    public static Method getMethod(String uri){
        return mapping.get(uri);
    }

    public static void main(String[] args) {
        System.out.println(mapping.size());
        System.out.println(mapping);
    }

}
