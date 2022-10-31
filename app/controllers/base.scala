package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel
import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String)



@Singleton
class base @Inject() (cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(8))(LoginData.apply)(LoginData.unapply))

  def login=Action{ implicit request =>
    Ok(views.html.login1(loginForm))
  }
  // def validateLoginGet(username:String,password: String)= Action{

    // Ok(s"$username got logged in with pass $password")
  // }

  def validateLoginPost = Action{ implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.validateUser(username,password)) {
        Ok(views.html.index(username)).withSession("username" -> username)
      } else{
        Redirect(routes.base.validateLoginPost).flashing("error" -> "Invalid password/username combination")
      }
      //      ok so routes. kerke uske aage ka ager link kisise match hota hai toh cool verna compile error
      //      Ager toh shi rha toh tashList daaldenge verna login wapas
      //      Ok(s"$username logged in with $password")
    }.getOrElse(Redirect(routes.base.validateLoginPost))
  }

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemoryModel.createUser(username, password)) {
        Ok(views.html.index(username)).withSession("username" -> username)
      } else {
        Redirect(routes.base.validateLoginPost)
      }
    }.getOrElse(Redirect(routes.base.validateLoginPost)).flashing("error" -> "User creation failed")
  }
  //flash stays up for one call, it goes away after that
  // so basically, request me store horha hai and username me stored tha usernameOption so get it?

  def world(username: String) = Action { implicit requset =>
      Ok(views.html.index(username)).withSession("username" -> username)
    }

  // def addTask = Action { implicit request =>
    // val usernameOption = request.session.get("username")
    // usernameOption.map { username =>
      // val postVals = request.body.asFormUrlEncoded
      // postVals.map { args =>
        // val task = args("newTask").head
        // TaskListInMemoryModel.addTask(username, task);
        // Redirect(routes.TaskList1.taskList)
      // }.getOrElse(Redirect(routes.TaskList1.world))
    // }.getOrElse(Redirect(routes.TaskList1.login))
  // }

  def product(prodType: String, prodNum: Int) = Action{
    Ok(s"Product Type is: $prodType, product number is: $prodNum")
  }



//  def deleteTask = Action { implicit request =>
//   val usernameOption = request.session.get("username")
    // usernameOption.map { username =>
      // val postVals = request.body.asFormUrlEncoded
      // postVals.map { args =>
        // val index = args("index").head.toInt
        // type casting babyyy
        // TaskListInMemoryModel.removeTask(username, index);
        // Redirect(routes.TaskList1.taskList)
      // }.getOrElse(Redirect(routes.TaskList1.world))
    // }.getOrElse(Redirect(routes.TaskList1.login))
  // }


  def logout = Action {
    Redirect(routes.base.validateLoginPost).withNewSession
  }

//  def validateLoginForm = Action { implicit request =>
//    loginForm.bindFromRequest.fold(
//      formWithErrors => BadRequest(views.html.login1(formWithErrors)),
//      ld =>
//        if (TaskListInMemoryModel.validateUser(ld.username, ld.password)) {
//          Redirect(routes.TaskList1.taskList).withSession("username" -> ld.username)
//        } else {
//          Redirect(routes.TaskList1.login).flashing("error" -> "Invalid password/username combination")
//        }
//    )
//
//  }

  // def createUserForm = Action { implicit request =>
    // loginForm.bindFromRequest.fold(
      // formWithErrors => BadRequest(views.html.login1(formWithErrors)),
      // ld =>
        // if (TaskListInMemoryModel.createUser(ld.username, ld.password)) {
          // Redirect(routes.TaskList1.taskList).withSession("username" -> ld.username)
        // } else {
          // Redirect(routes.TaskList1.login).flashing("error" -> "User creation failed.")
        // })
  // }



}