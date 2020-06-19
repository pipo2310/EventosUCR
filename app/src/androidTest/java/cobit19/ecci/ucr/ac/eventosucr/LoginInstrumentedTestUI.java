package cobit19.ecci.ucr.ac.eventosucr;

import android.app.Activity;
import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import cobit19.ecci.ucr.ac.eventosucr.features.login.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTestUI {
    public static final String TEST_STRING_EMPTY = "";
    public static final String TEST_USER_HINT = "Correo UCR";
    public static final String TEST_PASS_HINT = "Contraseña";
    public static final String TEST_RESET_PASS_TEXT = "Olvidó la contraseña?";
    public static final String TEST_REG_TEXT = "Crear una cuenta";
    public static final String TEST_LOGIN_USER = "walter.bonillagutierrez@ucr.ac.cr";
    public static final String TEST_LOGIN_PASS = "Hola1234";

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLoginText(){
        onView(withId(R.id.login_nombre_usuario)).check(matches(withText(TEST_STRING_EMPTY)));
        onView(withId(R.id.login_contrasenna)).check(matches(withText(TEST_STRING_EMPTY)));
        onView(withId(R.id.login_reset_pass)).check(matches(withText(TEST_RESET_PASS_TEXT)));
        onView(withId(R.id.login_registro)).check(matches(withText(TEST_REG_TEXT)));
    }

    @Test
    public void testLoginHint(){
        onView(withId(R.id.login_nombre_usuario)).check(matches(withHint(TEST_USER_HINT)));
        onView(withId(R.id.login_contrasenna)).check(matches(withHint(TEST_PASS_HINT)));
    }

    @Test
    public void testLoginSuccess() {
        // Cerramos la sesion que iniciamos para la prueba
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        onView(withId(R.id.login_nombre_usuario)).perform(typeText(TEST_LOGIN_USER));
        onView(withId(R.id.login_contrasenna)).perform(typeText(TEST_LOGIN_PASS), closeSoftKeyboard());
        onView(withId(R.id.login_boton_is)).perform(click());
        Class<?> activity = getActivityInstance().getClass();
        // Cerramos la sesion que iniciamos para la prueba
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        // Comparamos el valor de la actividad
        Assert.assertEquals(activity, MenuActivity.class);
    }

    private Activity getActivityInstance() {
        // Esperamos un poco a que cargue la nueva Actividad
        SystemClock.sleep(5000);
        final Activity[] currentActivity = {null};
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity[0] = (Activity) resumedActivities.iterator().next();
                }
            }
        });
        return currentActivity[0];
    }
}
