package andrevictor.com.jarbas.ClassesDiversas;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreVictor on 05/10/16.
 * Classe que mexe com as permissões
 */

public class PermissionUtils {
    //Solicita permissoes
    public static boolean validate(Activity activity, int requestCode, String... permissions){
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            //Valida permissao
            boolean ok = ContextCompat.checkSelfPermission(activity,permission) == PackageManager.PERMISSION_GRANTED;
            if(! ok){
                list.add(permission);
            }
        }
        if(list.isEmpty()){
            //Tudo ok , retorna true
            return true;
        }

        //Lista de permissoes que falta acesso.
        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        //Solicita permissao
        ActivityCompat.requestPermissions(activity,newPermissions,1);

        return false;
    }
}
