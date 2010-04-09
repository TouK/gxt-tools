package pl.touk.wonderfulsecurity

import org.apache.commons.logging.LogFactory
import pl.touk.wonderfulsecurity.dao.WsecUserDao
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.beans.WsecGroup
import pl.touk.wonderfulsecurity.dao.WsecGroupDao
import pl.touk.wonderfulsecurity.beans.WsecRole
import org.apache.commons.logging.Log
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.beans.WsecUser
import pl.touk.wonderfulsecurity.beans.WsecPermission
import pl.touk.wonderfulsecurity.dao.WsecRoleDao
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao

/**
 *
 * Tools to prepare fixtures for unit tests and for demo application
 *
 * @author Łukasz Kucharski - lkc@touk.pl
 */
class FixtureUtils {

    private static final Log log = LogFactory.getLog(FixtureUtils.class)

    private static def users = [
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,22333,czesio,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,22333,rysio,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,wiesia,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,iza,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,ala,test,true",
            "Ola,Olowa,ViewGenerated,ola@touk.pl,Olowa,Olowo,55333,MailOlaa,mailOlowo,44333,ola,test,true",
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,22333,11,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,22333,111,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,1111,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,11111,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,111111,test,true",
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,22333,112,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,22333,1112,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,11112,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,111112,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,1111112,test,true",
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,22333,2112,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,22333,21112,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,211112,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,2111112,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,21111112,test,true",
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,522333,511,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,522333,5111,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,51111,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,511111,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,5111111,test,true",
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,22333,5112,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,22333,51112,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,511112,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,5111112,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,51111112,test,true",
            "Czesio,Czesiarski,ViewGenerated,czesio@touk.pl,Czesiarska,Czesiowo,22333,MailCzesiarska,MailCzesiowo,22333,52112,test,true",
            "Rysiu,Rysiowski,ViewGeneraed,rysiu@touk.pl,Rysiowa,Rysiowo,22333,MailRysiowa,MailRysiowo,22333,521112,test,true",
            "Wiesia,Wiesiowska,ViewGenerated,wiesia@touk.pl,Wiesiowa,Wiesiowo,33222,MailWiesiowa,MailWiesiowo,33322,5211112,test,true",
            "Iza,Izowska,ViewGenerated,iza@touk.pl,Izowa,Izowo,55544,MailIzowa,MailIzowo,33222,52111112,test,true",
            "Ala,Alowska,ViewGenerated,ala@touk.pl,Alowa,Alowo,33222,MailAlowa,MailAlowo,66544,521111112,test,true",
            "Jadzia,jadziowska,viewgenerated,jadzia@touk.pl,Jadziowa,jadziowo,44533,mailJadziowska,mailJadziowo,555444,jadzia,test,true"
    ]

    private static def groups = [
            "GROUP_SECURITY_ADMINISTRATOR,Najwazniejsza grupa w systemie posiada uprawnienia do nadawania i udbierania uprawnien innym uzytkownikom",
            "GROUP_SECURITY_AUDIT,Blah blab sld lsdjfls ja a;sfja ;sdlkjf akdjfa lkja ;dkjfa ;sdlkfj a;lsjfa;skfa a;skdfa;k a;sdkjf",
            "GROUP_LOOSERS,Loooooo ser s  a;dfaj;slafk asd; lkas;dfl a;sdf a;sdf kaj;sdf lasdkf ja;sdl asdf a;sdd",
            "GROUP_USERS, Standardowa grupa Uzytkownikow"
    ]

    private static def roles = [
            "ROLE_SECURITY_ADMINISTRATOR,Najwazniejsza rola w systemie posiada uprawnienia do nadawania i udbierania uprawnien innym uzytkownikom",
            "ROLE_FE_USER, Standardowe uprawnienia uzytkownika",
          /*  "ROLE_FE_USER1, Standardowe uprawnienia uzytkownika1",
            "ROLE_FE_USER2, Standardowe uprawnienia uzytkownika2",
            "ROLE_FE_USER3, Standardowe uprawnienia uzytkownika3",
            "ROLE_FE_USER4, Standardowe uprawnienia uzytkownika4",
            "ROLE_FE_USER5, Standardowe uprawnienia uzytkownika5",
            "ROLE_FE_USER6, Standardowe uprawnienia uzytkownika6",
            "ROLE_FE_USER7, Standardowe uprawnienia uzytkownika7",
            "ROLE_FE_USER8, Standardowe uprawnienia uzytkownika8",
            "ROLE_FE_USER9, Standardowe uprawnienia uzytkownika9",
            "ROLE_FE_USER10, Standardowe uprawnienia uzytkownik0a10",
            "ROLE_FE_USER11, Standardowe uprawnienia uzytkownika11",
            "ROLE_FE_USER12, Standardowe uprawnienia uzytkownika12",
            "ROLE_FE_USER13, Standardowe uprawnienia uzytkownika13",*/
            "ROLE_WS_USER, Standardowe uprawnienia uzytkownika"
    ]

    private static def permissions = [
            "PERMISSION_NO11, Uprawnienie małe",
            "PERMISSION_NO12, Uprawnienie małe",
            "PERMISSION_NO13, Uprawnienie małe",
            "PERMISSION_NO14, Uprawnienie małe",
            "PERMISSION_NO15, Uprawnienie małe",
            "PERMISSION_NO16, Uprawnienie małe",
            "PERMISSION_NO17, Uprawnienie małe",
            "PERMISSION_NO18, Uprawnienie małe",
            "PERMISSION_NO19, Uprawnienie małe",
        /*    "PERMISSION_NO110, Uprawnienie małe",
            "PERMISSION_NO111, Uprawnienie małe",
            "PERMISSION_NO112, Uprawnienie małe",
            "PERMISSION_NO113, Uprawnienie małe",
            "PERMISSION_NO114, Uprawnienie małe",
            "PERMISSION_NO115, Uprawnienie małe",
            "PERMISSION_NO116, Uprawnienie małe",
            "PERMISSION_NO117, Uprawnienie małe",
            "PERMISSION_NO118, Uprawnienie małe",
            "PERMISSION_NO119, Uprawnienie małe",
            "PERMISSION_NO120, Uprawnienie małe",
            "PERMISSION_NO121, Uprawnienie małe",
            "PERMISSION_NO122, Uprawnienie małe",
            "PERMISSION_NO123, Uprawnienie małe",
            "PERMISSION_NO124, Uprawnienie małe",
            "PERMISSION_NO125, Uprawnienie małe",
            "PERMISSION_NO126, Uprawnienie małe",
            "PERMISSION_NO127, Uprawnienie małe",
            "PERMISSION_NO128, Uprawnienie małe",
            "PERMISSION_NO129, Uprawnienie małe",
            "PERMISSION_NO130, Uprawnienie małe",
            "PERMISSION_NO131, Uprawnienie małe",
            "PERMISSION_NO132, Uprawnienie małe",
            "PERMISSION_NO133, Uprawnienie małe",
            "PERMISSION_NO134, Uprawnienie małe",*/
            "PERMISSION_NO1, Uprawnienie małe",
            "PERMISSION_NO2, Uprawnienie duze"
    ]


    public static def USERS = null;
    public static def GROUPS = null;
    public static def ROLES = null;
    public static def PERMISSIONS = null;


    public static void prepareUserTestFixture(userDao, groupDao, roleDao, permissionDao){

        USERS = users.collect{
            it.tokenize(",") as WsecUser
        }

        GROUPS = groups.collect{
            it.tokenize(",") as WsecGroup
        }

        ROLES = roles.collect{
            it.tokenize(",") as WsecRole
        }

        PERMISSIONS = permissions.collect {
            it.tokenize(",") as WsecPermission
        }
        userDao.saveOrUpdateAll  USERS
        groupDao.saveOrUpdateAll  GROUPS
        roleDao.saveOrUpdateAll  ROLES
        permissionDao.saveOrUpdateAll PERMISSIONS


    }

    public static void prepareFixtureForUserGroupAssociation(WsecUserDao userDao, WsecGroupDao groupDao){
        WsecUser czesio = userDao.getUserByLogin("czesio")
        WsecGroup user = groupDao.getGroupByName("GROUP_USERS")

        czesio.addToGroup user
    }

    
    public static void prepareFixtureForGroupRoleAssociation(groupDao, roleDao){
        
        WsecGroup groupUsers = groupDao.getGroupByName("GROUP_USERS")
        WsecRole roleWsUser = roleDao.getRoleByName("ROLE_WS_USER")
        groupUsers.addRole roleWsUser
    }

    public static prepareFixtureForUserHierarchyTesting(WsecUserDao userDao){

        log.info "Getting czesio"

        WsecUser czesio = userDao.getUserByLogin("czesio")
        WsecUser rysio = userDao.getUserByLogin("rysio")
        WsecUser wiesia = userDao.getUserByLogin("wiesia")
        WsecUser iza = userDao.getUserByLogin("iza")

        log.info "Got iza"

        log.info "setting rysio as czesio supervisor"
        czesio.supervisor = rysio

        log.info "Adding wiesia as czesio's subordinate"
        czesio.addSubordinate wiesia

        log.info "Adding iza as czesio's subordinate"
        czesio.addSubordinate iza
        
        
    }

	public static void prepareFixtureForPermissionCollisionTesting(WsecUserDao wsecUserDao, WsecRoleDao wsecRoleDao, WsecGroupDao wsecGroupDao, WsecPermissionDao wsecPermissionDao){

		WsecUser czesio = wsecUserDao.getUserByLogin("czesio")
		WsecPermission wsecPermission = wsecPermissionDao.getPermissionByName("PERMISSION_NO11")
		czesio.addPermission(wsecPermission)
		Set<WsecPermission> exclusions = new HashSet<WsecPermission>()
		exclusions.add(wsecPermissionDao.getPermissionByName("PERMISSION_NO12"))
		wsecPermission.setExcludes(exclusions)


		WsecGroup wsecGroup = wsecGroupDao.getGroupByName("GROUP_LOOSERS")
		czesio.addToGroup(wsecGroup)
		Set<WsecPermission> groupExclusions = new HashSet<WsecPermission>()
		groupExclusions.add(wsecPermissionDao.getPermissionByName("PERMISSION_NO14"))
		wsecPermission = wsecPermissionDao.getPermissionByName("PERMISSION_NO13")
		wsecPermission.setExcludes(groupExclusions)
		wsecGroup.addPermission(wsecPermission)

		WsecRole wsecRole = wsecRoleDao.getRoleByName("ROLE_WS_USER")
		czesio.addRole(wsecRole)
		Set<WsecPermission> roleExclusions = new HashSet<WsecPermission>()
		roleExclusions.add(wsecPermissionDao.getPermissionByName("PERMISSION_NO16"))
		wsecPermission = wsecPermissionDao.getPermissionByName("PERMISSION_NO15")
		wsecPermission.setExcludes(roleExclusions)
		wsecRole.addPermission(wsecPermission)

	}

	public static void prepareFixtureForPermissionBidirectionalRelationsTest(WsecUserDao wsecUserDao, WsecRoleDao wsecRoleDao, WsecGroupDao wsecGroupDao, WsecPermissionDao wsecPermissionDao){
		wsecUserDao.getUserByLogin("rysio").addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO1"))
		wsecRoleDao.getRoleByName("ROLE_WS_USER").addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO1"))
		wsecRoleDao.getRoleByName("ROLE_WS_USER").addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO19"))
		wsecRoleDao.getRoleByName("ROLE_SECURITY_ADMINISTRATOR").addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO19"))
		wsecGroupDao.getGroupByName("GROUP_LOOSERS").addPermission(wsecPermissionDao.getPermissionByName("PERMISSION_NO1"))
	}

    public static void cleanTextFixture(WsecUserDao userDao){
        
    }



}