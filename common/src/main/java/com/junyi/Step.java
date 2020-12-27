package com.junyi;

/**
 * @time: 2020/12/24 19:04
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public enum Step {
    START, // 开始
    GETCARTYPE, // 获取车辆类型
    BLACKLIST, // 黑名单
    SPECIALCAR, // 特种车
    FREECAR, // 免费车
    NULL, // 不属于黑名单，特种车，免费车

    FINDINREORD,  //查询入场记录
    SENDSIMILAR, // 发送相似车牌
    NOINREORD,  //没有唯一的入场记录

    ISSMALLBIG, // 获取大小车场
    SMALLPARK,  //小车场
    BIGPARK,   //大车场

    ISFULLIN, FULLALLOWIN, // 满位允许入场
    FULLNOTALLOWIN, // 满位不允许入场

    ISREADSUCCESS, // 是否识别成功
    READFAIL, // 识别失败

    CARDTYPE, // 卡片类型
    ISREGULARCAR, // 判断是否为固定车
    REGULARCAR, // 固定车
    TEMPCAR, // 临时车
    MONTHCAR, // 月租车
    MONTHTEMPCAR, // 月临车
    FERRCAR,   // 免费车
    STORECAR, // 储值车
    STORETEMPCAR, // 储临车

    MONTHMOREOUT, //固定车多出

    CHANNELPERMISSIONS,//未勾选通道权限
    HASGULARPOWER, // 有固定车通道权限
    NOGULARPOWER, // 没有通道权限

    TEMPCHANNELPERMISSIONS,//临时车通道权限
    VOLUATIONTEMP,//临时车赋值
    CHECKEFFECT, // 检查有效期
    EXPIRE, // 固定车过期
    UNDUE, // 未到有效期
    TIMENOTALLOW, // 时间段禁止进入
    EFFECTIVE, // 有效期内
    UNEFFECTIVE, // 不在有效期内
    UNEFFECTIVEANDFAMILY,//不在有效期但是车位组
    UNEFFECTIVEONLY,//单纯不在有效期
    UNEFFECTIVEANDFAMILYMONEY,//家庭组车辆出场过期计费处理

    FAMILYTEMP, // 家组临时卡
    FAMILYMONTH, // 家庭组月卡
    FAMILYFULL, // 家庭组满位

    FAMILY, // 家庭组
    NOTFAMILY, // 非家庭组

    CHECKTEMPPOWER, // 检查临时车通道权限
    HASTEMPPOWER, // 临时车通道权限
    NOTEMPPOWER, // 没有临时车通道权限

    PRESETCARTYPE, // 入场临时车赋值

    ISARREARS, // 是否欠费
    ARREARS, // 欠费
    NOPAY,   //无需缴费

    ISNEEDCONFIRM, // 判断开闸是否需要确认

    NEEDCONFIRM, // 开闸需要确认

    HAS_DISCERN_MONEY_MACH,//有纸币机，纸币机的话就不用播报语音了

    CHECKSMALLPARKCHARGE,	// 嵌套车场检查小车场费用是否结清

    ISLOCK, // 是否锁车
    LOCK, // 锁车
    UNLOCK, // 锁车

    BEFORECHARGE,

    NOCARNO, //无牌车

    SAVE, // 保存

    VOICE, // 报语音
    OPEN, // 开闸

    WAIT,//等待岗亭处理（或者等待支付）

    END // 结束
}