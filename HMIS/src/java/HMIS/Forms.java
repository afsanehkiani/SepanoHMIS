/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HMIS;

import cms.access.Access_User;
import cms.tools.Js;
import cms.tools.Server;
import cms.tools.jjTools;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.swing.table.DefaultTableModel;
import jj.jjCalendar_IR;
import jj.jjDatabaseWeb;
import jj.jjNumber;

/**
 *
 * @author Mohammad
 */
public class Forms {

    public static final String tableName = "hmis_forms";
    public static final String _id = "id";
    public static final String _code = "forms_code";
    public static final String _title = "forms_title";
    public static final String _category_id = "forms_category_id";
    public static final String _priority = "forms_priority";
//    دسترسی های یک فرم برای پر کردن فرم ها
    public static final String _departments = "forms_departments";//بخش یا بخش هایی که این فرم را باید ببینند
    public static final String _accessessRoles = "forms_accesseRoles";//سمت هایی که به این فرم دسترسی 
    public static final String _accessessUsers = "forms_accessesUsers";// اشخاصی که به این فرم دسترسی 
//    
    public static final String _tags = "forms_tags";//برچسب هایی که میتوان به این فرم داد و بر اساس برچسب ها در بخش های مختلف نشان داده شود
    public static final String _icon = "forms_icon";
    public static final String _description = "forms_discription";
    public static final String _htmlContent = "forms_htmlContent";
    public static final String _ownerId = "forms_ownerId";//آی دی ایجاد کننده ی فرم
    public static final String _ownerRole = "forms_ownerRole";//سمت ایجاد کننده ی فرم
    public static final String _isActive = "forms_isActive";//سمت ایجاد کننده ی فرم
    public static final String _creationDate = "forms_creationDate";
    public static final String _creationTime = "forms_creationTime";
    public static final String _expireDate = "forms_expireDate";
    public static final String _expireTime = "forms_expireTime";
    public static final String _css = "forms_css";// برای قراردادن کد های سی اس اس
    public static final String _javaScript = "forms_javaScript";// برای قراردادن کد های جاوا اسکریپت
    public static final String _visit = "forms_visit";
    public static final String _nextFormId = "forms_nextFormId";// آی دی فرم بعدی که بعد از این فرم باید لود بشود
    public static final String _isAutoWiki = "forms_isAutoWiki";//برای این فرم لینک اتو ویکی در محتوا ساخته شود یا خیر
    public static final String _hasAutoWikiInContent = "forms_hasAutoWikiInContent";//در محتوای این فرم اتو ویکی فعال باشد یا نه

    public static final String lbl_insert = "ثبت و افزودن سوال";
    public static final String lbl_delete = "حذف فرم";
    public static final String lbl_edit = "ویرایش";
    public static final String lbl_add_en = "افزودن زبان انگلیسی";
    public static final String lbl_edit_en = "ویرایش بخش انگلیسی";
    public static final String lbl_add_ar = "افزودن زبان عربی";
    public static final String lbl_edit_ar = "ویرایش بخش عربی";

    public static int rul_rfs = 0;//60;
    public static int rul_rfs_own = 0;// 61;//فقط امکان دیدن فرم های ایجاد شده ی توسط خود ایجاد کننده را دارد // بر روی سمت
    public static int rul_ins = 0;// 62;
    public static int rul_edt = 0;// 63;
    public static int rul_dlt = 0;// 64;
    public static int rul_5 = 0;// 65;
    public static int rul_6 = 0;// 66;
    public static int rul_7 = 0;// 67;
    public static int rul_8 = 0;// 68;
    public static int rul_9 = 0;// 69;
    public static int rul_10 = 0;// 70;

//    public static int rul_lng5 = 68;
    public static final String lbl_add_ln2 = "برچسب";
    public static final String lbl_edit_ln2 = "ویرایش بخش انگلیسی";
    public static final String lbl_add_ln3 = "افزودن زبان عربی";
    public static final String lbl_edit_ln3 = "ویرایش بخش عربی";
    public static final String lbl_add_ln4 = "افزودن زبان آلمانی";
    public static final String lbl_edit_ln4 = "ویرایش بخش آلمانی";
    public static final String lbl_add_ln5 = "افزودن زبان چینی";
    public static final String lbl_edit_ln5 = "ویرایش بخش چینی";

    public static String refresh(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_rfs);
            if (!hasAccess.equals("")) {
                return hasAccess;
            }
            StringBuilder html = new StringBuilder();
            DefaultTableModel dtm = db.Select(tableName);//@ToDo فقط ستون هایی که لازم هست را بگیریم که در مصرف حاقظه رم سرفه جویی بشود
            List<Map<String, Object>> row = jjDatabaseWeb.separateRow(dtm);
            html.append("<div class='card-header bg-primary tx-white'>لیست فرم ها و چک لیست های تعریف شده</div>\n");
            html.append("<div class='card-body pd-sm-30'>");
            boolean accIns = Access_User.hasAccess2(request, db, rul_ins);
            if (accIns) {
                html.append("<p class='mg-b-20 mg-sm-b-30'>");
                html.append("<a style='color:#fff' class='btn btn-success pd-sm-x-20 mg-sm-r-5 tx-white' onclick='hmisForms.m_add_new();' >فرم یا چک لیست جدید</a>");
                html.append("</p>");
            }
            html.append("<div class='table-wrapper'>");
            html.append("<table id='refreshForms' class='table display responsive' class='tahoma10' style='direction: rtl'><thead>");
            html.append("<th width='5%' class='r'>کد</th>");
            html.append("<th width='20%' class='r'>عنوان فرم</th>");
            html.append("<th width='20%' class='c'>ویرایش و اصلاح</th>");
            html.append("<th width='20%' class='c'>آنالیز و آمار</th>");
            html.append("</thead><tbody>");
            for (int i = 0; i < row.size(); i++) {
                html.append("<tr  class='mousePointer'>");
                html.append("<td class='r'>" + row.get(i).get(_code) + "</td>");
                html.append("<td class='r'>" + row.get(i).get(_title) + "</td>");
                html.append("<td class='c'><i class='p icon ion-ios-gear-outline' onclick='" + Js.jjForms.select(row.get(i).get(_id).toString()) + "' style='color:#ffcd00!important'></i></td>");
                html.append("<td class='c'><i class='p fa fa-bar-chart' onclick='" + Js.jjForms.select(row.get(i).get(_id).toString()) + "'></i></td>");
                html.append("</tr>");
            }
            html.append("</tbody></table>");
            html.append("</div>");
            String height = jjTools.getParameter(request, "height");
            html.append("</div>");
            String panel = jjTools.getParameter(request, "panel");
            if (!jjNumber.isDigit(height)) {
                height = "";
            }
            if (panel.equals("")) {
                panel = "swNewsTbl";
            }
            String script = Js.setHtml("#" + panel, html.toString());
            script += Js.table("#refreshForms", height, 0, Access_User.getAccessDialog(request, db, rul_ins).equals("") ? "2" : "", "لیست اخبار");
            return script;
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    public static String add_new(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            StringBuilder script = new StringBuilder();
            boolean accIns = Access_User.hasAccess2(request, db, rul_ins);
            if (accIns) {

            }
            if (accIns) {
                List<Map<String, Object>> userRow = jjDatabaseWeb.separateRow(db.Select(Access_User.tableName, _id + "=" + jjTools.getSeassionUserId(request)));//برای استخراج نام و نام خانوادگی کاربری که در سشن فعال است
                script.append(Js.setVal(_ownerId, jjTools.getSeassionUserId(request)));
                script.append(Js.setVal("#forms_ownerName", userRow.get(0).get(Access_User._name).toString() + " " + userRow.get(0).get(Access_User._family).toString()));
                script.append(Js.setVal(_ownerRole, jjTools.getSeassionUserRole(request)));
                List<Map<String, Object>> userRoleRow = jjDatabaseWeb.separateRow(db.Select(Access_User.tableName, _id + "=" + jjTools.getSeassionUserRole(request)));//برای در آوردن نقش کاربر موجود
                if (!userRoleRow.isEmpty()) {// ممکن است کاربر جاری نقشی در سیستم نداشته باشد ولی دسترسی هایی داشته باشد
                    script.append(Js.setVal("#forms_ownerRoleTitle", userRoleRow.get(0).get(Access_User._name).toString() + " " + userRoleRow.get(0).get(Access_User._family).toString()));
                }
                script.append(Js.setHtml("#forms_buttons", "<div class='col-lg-6'><input type='button' id='insert_Forms_new'  value=\"" + lbl_insert + "\" class='btn btn-outline-success active btn-block mg-b-10'></div>"));
                script.append(Js.click("#insert_Forms_new", Js.jjForms.insert()));
            } else {
                script.append(Js.setHtml("#forms_buttons", ""));
            }
            return script.toString();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    /**
     *
     *
     * @param request
     * @param db
     * @param isPost
     * @return
     * @throws Exception
     */
    public static String insert(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_ins);
            if (!hasAccess.equals("")) {
                return Js.modal(hasAccess, "پیام سامانه");
            }
            jjCalendar_IR ir = new jjCalendar_IR();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(_title, jjTools.getParameter(request, _title));
            map.put(_code, jjTools.getParameter(request, _code));
            map.put(_departments, jjTools.getParameter(request, _departments));
            map.put(_isActive, jjTools.getParameter(request, _isActive));
            map.put(_icon, jjTools.getParameter(request, _icon));
            map.put(_ownerId, jjTools.getSeassionUserId(request));
            map.put(_ownerRole, jjTools.getParameter(request, _ownerRole));
            map.put(_accessessUsers, jjTools.getParameter(request, _accessessUsers));
            map.put(_accessessRoles, jjTools.getParameter(request, _accessessRoles));
            if ("".equals(jjTools.getParameter(request, _creationDate))) {// اگر تاریخ شروع اعتبار وارد نکرده بود
                map.put(_creationDate, jjCalendar_IR.getDatabaseFormat_8length(null, true));
            } else {
                map.put(_creationDate, jjCalendar_IR.getDatabaseFormat_8length(jjTools.getParameter(request, _accessessRoles), true));
            }
            jjCalendar_IR date = new jjCalendar_IR();
            map.put(_creationTime, jj.jjTime.getTime4lenth(jjTools.getParameter(request, _creationTime)));
            map.put(_expireDate, jjCalendar_IR.getDatabaseFormat_8length(jjTools.getParameter(request, _expireDate), false));
            map.put(_expireTime, jj.jjTime.getTime4lenth(jjTools.getParameter(request, _expireTime)));
            map.put(_nextFormId, jjNumber.isDigit(jjTools.getParameter(request, _nextFormId)));
            map.put(_isAutoWiki, jjTools.getParameter(request, _isAutoWiki));
            map.put(_hasAutoWikiInContent, jjTools.getParameter(request, _hasAutoWikiInContent));
            map.put(_css, jjTools.getParameter(request, _css));
            map.put(_javaScript, jjTools.getParameter(request, _javaScript));
            map.put(_htmlContent, jjTools.getParameter(request, _htmlContent));
            map.put(_description, jjTools.getParameter(request, _description));

            List<Map<String, Object>> insertedFormRow = jjDatabaseWeb.separateRow(db.insert(tableName, map));
            StringBuilder script = new StringBuilder();
            if (insertedFormRow.isEmpty()) {
                String errorMessage = "عملیات درج به درستی صورت نگرفت.";
                return Js.modal(errorMessage, "پیام سامانه");
            }
            script.append(Js.jjForms.refresh());
            //کاربر بعد از ثبت مشخصات فرم یاد سوالات فرم را یکی یکی یا دسته ای اضافه کند
            script.append(Js.jjForms.select(insertedFormRow.get(0).get(_id).toString()));// برای اینکه در واقع مثل موقعی بشود که یک فرم قبلا ثبت شده را انتخاب کرده سلکت جاوا اسکریپتی را بعد از اینسرت صدا میزنیم
            return script.toString();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    public static String select(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_rfs);
            if (!hasAccess.equals("")) {
                return Js.modal(hasAccess, "پیام سامانه");
            }

            String id = jjTools.getParameter(request, _id);
            List<Map<String, Object>> formRow = jjDatabaseWeb.separateRow(db.Select(tableName, _id + "=" + id));
            if (formRow.isEmpty()) {
                String errorMessage = "فرم مورد نظر یافت نشد";
                return Js.modal(errorMessage, "پیام سامانه");
            }
            StringBuilder script = new StringBuilder();
            Map<String, Object> map = new HashMap<String, Object>();
            script.append(Js.setVal("#" + tableName + "_id", formRow.get(0).get(_id).toString()));
            script.append(Js.setVal("#" + _title, formRow.get(0).get(_title).toString()));
            script.append(Js.setVal("#" + _code, formRow.get(0).get(_code).toString()));
            script.append(Js.setVal("#" + _departments, formRow.get(0).get(_departments).toString()));
            script.append("$('#forms_departments').select2();\n");
            script.append(Js.setVal("#" + _isActive, formRow.get(0).get(_isActive).toString()));
            script.append(Js.setVal("#" + _icon, formRow.get(0).get(_icon).toString()));
            if (!formRow.get(0).get(_icon).toString().isEmpty()) {//اگر عکس داشت نشان بدهد
                script.append(Js.setAttr("#forms_icon_Preview", "src", "upload/" + formRow.get(0).get(_icon).toString()));
            }
            script.append(Js.setVal("#" + _ownerId, formRow.get(0).get(_ownerId).toString()));
            script.append(Js.setVal("#" + _ownerRole, formRow.get(0).get(_ownerRole).toString()));
            script.append(Js.setVal("#" + _accessessUsers, formRow.get(0).get(_accessessUsers).toString()));
            script.append(Js.setVal("#" + _accessessRoles, formRow.get(0).get(_accessessRoles).toString()));

            script.append(Js.setVal("#" + _creationDate, jjCalendar_IR.getViewFormat(formRow.get(0).get(_creationDate).toString())));
            script.append(Js.setVal("#" + _expireDate, jjCalendar_IR.getViewFormat(formRow.get(0).get(_expireDate).toString())));
            jjCalendar_IR date = new jjCalendar_IR();

            script.append(Js.setVal("#" + _creationTime, jj.jjTime.getTime5lenth(formRow.get(0).get(_creationTime).toString())));
            script.append(Js.setVal("#" + _expireTime, jj.jjTime.getTime5lenth(formRow.get(0).get(_expireTime).toString())));

            script.append(Js.setVal("#" + _nextFormId, formRow.get(0).get(_nextFormId).toString()));
            script.append(Js.setVal("#" + _isAutoWiki, formRow.get(0).get(_isAutoWiki).toString()));
            script.append(Js.setVal("#" + _hasAutoWikiInContent, formRow.get(0).get(_hasAutoWikiInContent).toString()));
            script.append(Js.setVal("#" + _css, formRow.get(0).get(_css).toString()));
            script.append(Js.setVal("#" + _javaScript, formRow.get(0).get(_javaScript).toString()));
            script.append(Js.setValSummerNote("#" + _htmlContent, formRow.get(0).get(_htmlContent).toString()));
            script.append(Js.setVal("#" + _description, formRow.get(0).get(_description).toString()));
            script.append(Js.setVal("#" + _description, formRow.get(0).get(_description).toString()));
            String htmlBottons = "";
            boolean accEdit = Access_User.hasAccess2(request, db, rul_edt);
            if (accEdit) {
                htmlBottons += "<div class='col-lg'><button title='" + lbl_edit + "' class='btn btn-outline-warning btn-block mg-b-10' onclick='" + Js.jjForms.edit(id) + "' id='edit_Forms_new'>" + lbl_edit + "</button></div>";
            }
            boolean accDelete = Access_User.hasAccess2(request, db, rul_dlt);
            if (accDelete) {
                htmlBottons += "<div class='col-lg'><button title='" + lbl_delete + "' class='btn btn-outline-danger btn-block mg-b-10' onclick='" + Js.jjForms.delete(id) + "' id='edit_Forms_new'>" + lbl_delete + "</button></div>";
            }
            script.append(Js.setHtml("#forms_buttons", htmlBottons));
            //کاربر بعد از ثبت مشخصات فرم یاد سوالات فرم را یکی یکی یا دسته ای اضافه کند
            return script.toString();
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }



    public static String edit(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_edt);
            if (!hasAccess.equals("")) {
                return Js.modal(hasAccess, "پیام سامانه");
            }

            String id = jjTools.getParameter(request, _id);
            jjCalendar_IR ir = new jjCalendar_IR();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(_title, jjTools.getParameter(request, _title));
            map.put(_code, jjTools.getParameter(request, _code));
            map.put(_departments, jjTools.getParameter(request, _departments));
            map.put(_isActive, jjTools.getParameter(request, _isActive));
            map.put(_icon, jjTools.getParameter(request, _icon));
            map.put(_ownerId, jjTools.getSeassionUserId(request));
            map.put(_ownerRole, jjTools.getParameter(request, _ownerRole));
            map.put(_accessessUsers, jjTools.getParameter(request, _accessessUsers));
            map.put(_accessessRoles, jjTools.getParameter(request, _accessessRoles));
            if ("".equals(jjTools.getParameter(request, _creationDate))) {// اگر تاریخ شروع اعتبار وارد نکرده بود
                map.put(_creationDate, jjCalendar_IR.getDatabaseFormat_8length(null, true));
            } else {
                map.put(_creationDate, jjCalendar_IR.getDatabaseFormat_8length(jjTools.getParameter(request, _accessessRoles), true));
            }
            jjCalendar_IR date = new jjCalendar_IR();
            map.put(_creationTime, jj.jjTime.getTime4lenth(jjTools.getParameter(request, _creationTime)));
            map.put(_expireDate, jjCalendar_IR.getDatabaseFormat_8length(jjTools.getParameter(request, _expireDate), false));
            map.put(_expireTime, jj.jjTime.getTime4lenth(jjTools.getParameter(request, _expireTime)));
            map.put(_nextFormId, jjNumber.isDigit(jjTools.getParameter(request, _nextFormId)));
            map.put(_isAutoWiki, jjTools.getParameter(request, _isAutoWiki));
            map.put(_hasAutoWikiInContent, jjTools.getParameter(request, _hasAutoWikiInContent));
            map.put(_css, jjTools.getParameter(request, _css));
            map.put(_javaScript, jjTools.getParameter(request, _javaScript));
            map.put(_htmlContent, jjTools.getParameter(request, _htmlContent));
            map.put(_description, jjTools.getParameter(request, _description));

            if (db.update(tableName, map, _id + "=" + id)) {
                return Js.modal("ویرایش بدرستی انجام شد", "پیام سامانه");
            } else {
                return Js.modal("ویرایش انجام نشد", "پیام سامانه");
            }
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

    public static String delete(HttpServletRequest request, jjDatabaseWeb db, boolean isPost) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_dlt);
            if (!hasAccess.equals("")) {
                return Js.modal(hasAccess, "پیام سامانه");
            } else {
                String id = jjTools.getParameter(request, _id);
                if (!jjNumber.isDigit(id)) {
                    return Js.modal("کد صحیح نیست", "پیام سامانه");
                }

//                db.Select(tableName)//در پاسخ ها اگر کسی پاسخ نداده است قابل حذف است@ToDo
                if (db.otherStatement("DELETE t0,t1,t2 FROM hmis_forms as t0 LEFT JOIN hmis_formquestions as t1 on t0.id=t1.formQuestions_formID LEFT JOIN hmis_formquestionoptions as t2 ON formQuestionOptions_question_id = t1.id WHERE t0.id=" + id)) {
                    return Js.jjForms.refresh() + Js.modal("همه سوال ها و گزینه ها حذف شدند", "پیام سامانه");
                } else {
                    return Js.modal("عدم موفقیت عملیات حذف!!!", "پیام سامانه");
                }
            }

        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }
    
    
        public static String showMyForms(HttpServletRequest request, jjDatabaseWeb db, boolean isAjax) throws Exception {
        try {
            String hasAccess = Access_User.getAccessDialog(request, db, rul_rfs);
            if (!hasAccess.equals("")) {
                return Js.modal(hasAccess, "پیام سامانه");
            }
            if (isAjax) {
                request.getRequestDispatcher("feiz/MyForms.jsp").forward(request, Server.Publicresponse);
                System.out.println("(((((((((((((((((((((((((((((((((((((((((((((");
                return "";
            } else {// اگر یک کلاس جاوایی ایا یک فایل جی اس پی ین نتایج را میخواهد لازم نیست به صفحه ی دیگری بوریم
                System.out.println("(((((((((((((((((((((((((((((((((((((((((((((");
                return "alert(54545454);";

            }
        } catch (Exception ex) {
            return Server.ErrorHandler(ex);
        }
    }

}
