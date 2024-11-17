package com.example.zekronhakeem.quranData

object QuranInfo {
    val ajzaa= listOf(
        Juz(1,1,1,148,"الفاتحة"),
        Juz(2,142,149,259,"البقرة"),
        Juz(3,253,260,385,"البقرة"),
        Juz(4,93,386,516,"آل عمران"),
        Juz(5,24,517,640,"النساء"),
        Juz(6,148,641,750,"النساء"),
        Juz(7,82,751,899,"المائدة"),
        Juz(8,111,900,1041,"الأنعام"),
        Juz(9,88,1042,1200,"الأعراف"),
        Juz(10,41,1201,1327,"الأنفال"),
        Juz(11,93,1328,1478,"التوبة"),
        Juz(12,6,1479,1648,"هود"),
        Juz(13,53,1649,1802,"يوسف"),
        Juz(14,1,1803,2029,"الحجر"),
        Juz(15,1,2030,2214,"الإسراء"),
        Juz(16,75,2215,2483,"الكهف"),
        Juz(17,1,2484,2673,"الأنبياء"),
        Juz(18,1,2674,2875,"المؤمنون"),
        Juz(19,21,2876,3214,"الفرقان"),
        Juz(20,56,3215,3385,"النمل"),
        Juz(21,46,3386,3563,"العنكبوت"),
        Juz(22,31,3564,3732,"الأحزاب"),
        Juz(23,28,3733,4089,"يس"),
        Juz(24,32,4090,4264,"الزمر"),
        Juz(25,47,4265,4510,"فصلت"),
        Juz(26,1,4511,4705,"الأحقاف"),
        Juz(27,31,4706,5104,"الذاريات"),
        Juz(28,1,5105,5241,"المجادلة"),
        Juz(29,1,5242,5672,"الملك"),
        Juz(30,1,5673,6236,"النبأ"),
        )
    val map= mutableMapOf<Int,String>(
        1 to "الفاتحة"  ,
        2 to "البقرة"   ,
        3 to "آل عمران" ,
        4 to "النساء "  ,
        5 to "المائدة"  ,
        6 to "الأنعام "   ,
        7 to "الأعراف "  ,
        8 to "الأنفال"    ,
        9 to "التوبة "  ,
        10 to "يونس	"   ,
        11 to "هود"     ,
        12 to "يوسف"    ,
        13 to "الرعد"	,
        14 to "إبراهيم"	,
        15 to "الحجر"   ,
        16 to "النحل"	,
        17 to "الإسراء"	,
        18 to "الكهف"   ,
        19 to "مريم	"   ,
        20 to "طه	"   ,
        21 to "الأنبياء" ,
        22 to "الحج	"   ,
        23 to "المؤمنون",
        24 to "النور"   ,
        25 to "الفرقان" ,
        26 to "الشعراء"	,
        27 to "النمل"   ,
        28 to "القصص"	,
        29 to "العنكبوت",
        30 to "الروم"   ,
        31 to "لقمان"	,
        32 to "السجدة"	,
        33 to "الأحزاب"  ,
        34 to "سبأ	"   ,
        35 to "فاطر	"   ,
        36 to "يس"      ,
        37 to "الصافات" ,
        38 to "ص	"   ,
        39 to "الزمر"   ,
        40 to "غافر	"   ,
        41 to "فصلت	"   ,
        42 to "الشورى"  ,
        43 to "الزخرف"	,
        44 to "الدخان"	,
        45 to "الجاثية" ,
        46 to "الأحقاف"	,
        47 to "محمد	"   ,
        48 to "الفتح"   ,
        49 to "الحجرات" ,
        50 to "ق	"   ,
        51 to "الذاريات",
        52 to "الطور"   ,
        53 to "النجم"   ,
        54 to "القمر"   ,
        55 to "الرحمن"  ,
        56 to "الواقعة" ,
        57 to "الحديد"  ,
        58 to "المجادلة",
        59 to "الحشر"   ,
        60 to "الممتحنة",
        61 to "الصف	"   ,
        62 to "الجمعة"  ,
        63 to "المنافقون",
        64 to "التغابن"	 ,
        65 to "الطلاق"	 ,
        66 to "التحريم"  ,
        67 to "الملك"	 ,
        68 to "القلم"	 ,
        69 to "الحاقة"   ,
        70 to "المعارج"	 ,
        71 to "نوح"      ,
        72 to "الجن"     ,
        73 to "المزمل"   ,
        74 to "المدثر"   ,
        75 to "القيامة"  ,
        76 to "الإنسان"   ,
        77 to "المرسلات"  ,
        78 to "النبأ"    ,
        79 to "النازعات ",
        80 to "عبس "     ,
        81 to "التكوير"  ,
        82 to "الانفطار " ,
        83 to "المطففين ",
        84 to "الانشقاق " ,
        85 to "البروج"   ,
        86 to "الطارق "  ,
        87 to "الأعلى"    ,
        88 to "الغاشية"  ,
        89 to "الفجر"	 ,
        90 to "البلد"    ,
        91 to "الشمس"    ,
        92 to "الليل"    ,
        93 to "الضحى"    ,
        94 to "الشرح"    ,
        95 to "التين"    ,
        96 to "العلق"    ,
        97 to "القدر"    ,
        98 to "البينة"   ,
        99 to "الزلزلة"  ,
        100 to "العاديات",
        101 to "القارعة	",
        102 to "التكاثر" ,
        103 to "العصر"   ,
        104 to "الهمزة"  ,
        105 to "الفيل"   ,
        106 to "قريش"    ,
        107 to "الماعون	",
        108 to "الكوثر"  ,
        109 to "الكافرون",
        110 to "النصر"   ,
        111 to "المسد"   ,
        112 to "الإخلاص"   ,
        113 to "الفلق"   ,
        114 to "الناس"
    )
}

data class Juz(val number:Int,
               val startingVerseInSurah:Int,
               val startingVerse:Int,
               val endingVerse:Int,
               val startingSurah:String)




