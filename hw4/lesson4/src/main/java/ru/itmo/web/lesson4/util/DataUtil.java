package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", User.Color.BLUE),
            new User(9, "geranazarov555", "Georgiy Nazarov", User.Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.RED)
    );

    private static final Post POST1 = new Post(1L, 1L, "Codeforces EDU: β-testing (Now in English!)",
            "Hello Codeforces!\n" +
                    "\n" +
                    "In February, in the Russian-language interface, we announced a new educational subsection of the website. It's time to go international!\n" +
                    "\n" +
                    "I am pleased to invite you to test the new section, which so far has the working title EDU. I hope that someday EDU will become a real educational hub for fans of programming competitions. It will be great to have in one place the collected educational content with text and video materials, with selected thematic problems.\n" +
                    "\n" +
                    "At the moment, its functionality is still under development, and here is the current progress and β-testing preview. Write your comments and suggestions in the comments here.\n" +
                    "\n" +
                    "So far, only a pilot course with the first lesson \"Suffix Array\" has been presented to your attention. The plans include expanding both the functionality of the section and filling it with new content.\n" +
                    "\n" +
                    "Russian version already contains more lessons. At the moment, it is important for us to get the first feedback from international users. Many thanks to Pavel Mavrin (pashka) for preparing the first English lesson. Please support pashka to motivate him to translate more lessons!\n" +
                    "\n" +
                    "I hope that in the future, using the materials in this section, you will be able to gain knowledge on an extensive set of topics that are used in contests and olympiads. According to the current plan, a group of ITMO lecturers and students will be engaged in a pilot course, a little later we will consider the possibility of opening the possibility of creating classes for a wider audience.\n" +
                    "\n" +
                    "Thanks to ITMO University for the idea and support for the implementation of this initiative!");

    private static final Post POST2 = new Post(2L, 1L, "Образовательный раздел Codeforces: β-тестирование",
            "Привет, Codeforces!\n" +
                    "\n" +
                    "Я рад пригласить вас к тестированию нового раздела, который пока имеет рабочее название EDU. Я надеюсь, что когда-нибудь EDU станет самым настоящим учебным хабом для любителей соревнований по программированию. Будет здорово иметь в одном месте собранный учебный контент — с текстовыми и видео материалами, с подобранными тематическими задачами.\n" +
                    "\n" +
                    "В настоящий момент функциональность раздела ещё находится состоянии разработки. Пока вашему вниманию представлен текущий прогресс в рамках β-тестирования. Не пугайтесь возможным ошибкам, недочётам и недоделкам — работа еще далека от завершения. Ваши отзывы и предложения пишите в комментариях к этому посту.\n" +
                    "\n" +
                    "Пока вашему вниманию представлен лишь пилотный курс с одним занятием «Суффиксный массив». В планах — расширение как функциональности раздела, так и наполнение его новым контентом. В настоящий момент нам важно получить первый фидбек. Огромное спасибо Павлу pashka Маврину за подготовку первого (и, надеюсь, далеко не последнего) занятия.\n" +
                    "\n" +
                    "Я надеюсь, что в будущем с помощью материалов этого раздела вы сможете получить знания по обширному набору тем, которые используются на соревнования и олимпиадах. По текущему плану пилотным курсом будет заниматься группа преподавателей ИТМО, чуть позже — рассмотрим возможность открыть возможность создания занятий на более широкую аудиторию.\n" +
                    "\n" +
                    "В настоящее время раздел доступен исключительно на русском языке.\n" +
                    "\n" +
                    "Спасибо Университету ИТМО за идею и поддержку реализации этой инициативы!");

    private static final Post POST3 = new Post(3L, 6L, "ITMO Algorithms Course",
            "Hello Codeforces!\n" +
                    "\n" +
                    "I teach a course on algorithms and data structures at ITMO University. During the last year I was streaming all my lectures on Twitch and uploaded the videos on Youtube.\n" +
                    "\n" +
                    "This year I want to try to do it in English.\n" +
                    "\n" +
                    "This is a four-semester course. The rough plan for the first semester:\n" +
                    "\n" +
                    "Algorithms, complexity, asymptotics\n" +
                    "Sorting algorithms\n" +
                    "Binary heap\n" +
                    "Binary search\n" +
                    "Linked lists, Stack, Queue\n" +
                    "Amortized analysis\n" +
                    "Fibonacci Heap\n" +
                    "Disjoint Set Union\n" +
                    "Dynamic Programming\n" +
                    "Hash Tables\n" +
                    "The lectures are open for everybody. If you want to attend, please fill out this form to help me pick the optimal day and time.\n" +
                    "\n" +
                    "See you!");

    private static final Post POST4 = new Post(4L, 11L, "touristream 012: Codeforces Round 672 (Div. 2)",
            "Tune in to my Twitch to watch me solve today's Codeforces Round 672 (Div. 2) virtually");

    private static final List<Post> POSTS = Arrays.asList(
            POST1, POST2, POST3, POST4
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
