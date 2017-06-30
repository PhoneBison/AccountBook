package com.stu.it.accountbook.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.stu.it.accountbook.db.Tag;
import com.stu.it.accountbook.db.Info;

import com.stu.it.accountbook.db.TagDao;
import com.stu.it.accountbook.db.InfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig tagDaoConfig;
    private final DaoConfig infoDaoConfig;

    private final TagDao tagDao;
    private final InfoDao infoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        tagDaoConfig = daoConfigMap.get(TagDao.class).clone();
        tagDaoConfig.initIdentityScope(type);

        infoDaoConfig = daoConfigMap.get(InfoDao.class).clone();
        infoDaoConfig.initIdentityScope(type);

        tagDao = new TagDao(tagDaoConfig, this);
        infoDao = new InfoDao(infoDaoConfig, this);

        registerDao(Tag.class, tagDao);
        registerDao(Info.class, infoDao);
    }
    
    public void clear() {
        tagDaoConfig.getIdentityScope().clear();
        infoDaoConfig.getIdentityScope().clear();
    }

    public TagDao getTagDao() {
        return tagDao;
    }

    public InfoDao getInfoDao() {
        return infoDao;
    }

}