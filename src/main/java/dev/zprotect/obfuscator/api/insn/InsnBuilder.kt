/**
 * Copyright (C) 2022 zProtect
 *
 * This software is NOT free software.
 * You may NOT distribute it or modify it without explicit permission.
 *
 * This software has been created in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY. zProtect is NOT liable for ANY damages caused by unlawful distribution
 * or usage of any zProtect source code or products.
 *
 * You should have received a copy of the zProtect PU (Prohibited Usage) License along with this program.
 */

package dev.zprotect.obfuscator.api.insn

import org.objectweb.asm.Handle
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class InsnBuilder {

    val insnList = InsnList()

    operator fun InsnList.unaryPlus() = insnList.add(this)
    operator fun AbstractInsnNode.unaryPlus() = insnList.add(this)
    fun Int.insn() = InsnNode(this)

    fun insn(opcode: Int) = +InsnNode(opcode)

    fun nop() = insn(NOP)

    fun aconst_null() = insn(ACONST_NULL)
    fun ldc(int: Int) = +getIntInsn(int)
    fun ldc(long: Long) = +getLongInsn(long)
    fun ldc(float: Float) = +getFloatInsn(float)
    fun ldc(double: Double) = +getDoubleInsn(double)
    fun ldc(string: String) = +LdcInsnNode(string)
    fun ldc(type: Type) = +LdcInsnNode(type)
    fun ldc(handle: Handle) = +LdcInsnNode(handle)

    fun istore(`var`: Int) = +VarInsnNode(ISTORE, `var`)
    fun iload(`var`: Int) = +VarInsnNode(ILOAD, `var`)
    fun lstore(`var`: Int) = +VarInsnNode(LSTORE, `var`)
    fun lload(`var`: Int) = +VarInsnNode(LLOAD, `var`)
    fun fstore(`var`: Int) = +VarInsnNode(FSTORE, `var`)
    fun fload(`var`: Int) = +VarInsnNode(FLOAD, `var`)
    fun dstore(`var`: Int) = +VarInsnNode(DSTORE, `var`)
    fun dload(`var`: Int) = +VarInsnNode(DLOAD, `var`)
    fun astore(`var`: Int) = +VarInsnNode(ASTORE, `var`)
    fun aload(`var`: Int) = +VarInsnNode(ALOAD, `var`)

    fun iastore() = insn(IASTORE)
    fun iaload() = insn(IALOAD)
    fun lastore() = insn(LASTORE)
    fun laload() = insn(LALOAD)
    fun fastore() = insn(FASTORE)
    fun faload() = insn(FALOAD)
    fun dastore() = insn(DASTORE)
    fun daload() = insn(DALOAD)
    fun aastore() = insn(AASTORE)
    fun aaload() = insn(AALOAD)
    fun bastore() = insn(BASTORE)
    fun baload() = insn(BALOAD)
    fun castore() = insn(CASTORE)
    fun caload() = insn(CALOAD)
    fun sastore() = insn(SASTORE)
    fun saload() = insn(SALOAD)


    fun pop() = insn(POP)
    fun pop2() = insn(POP2)
    fun dup() = insn(DUP)
    fun dup_x1() = insn(DUP_X1)
    fun dup_x2() = insn(DUP_X2)
    fun dup2() = insn(DUP2)
    fun dup2_x1() = insn(DUP2_X1)
    fun dup2_x2() = insn(DUP2_X2)
    fun swap() = insn(SWAP)


    fun iadd() = insn(IADD)
    fun isub() = insn(ISUB)
    fun imul() = insn(IMUL)
    fun idiv() = insn(IDIV)
    fun irem() = insn(IREM)
    fun ineg() = insn(INEG)
    fun ishl() = insn(ISHL)
    fun ishr() = insn(ISHR)
    fun iushr() = insn(IUSHR)
    fun iand() = insn(IAND)
    fun ior() = insn(IOR)
    fun ixor() = insn(IXOR)
    fun iinc(`var`: Int, incr: Int) = +IincInsnNode(`var`, incr)

    fun ladd() = insn(LADD)
    fun lsub() = insn(LSUB)
    fun lmul() = insn(LMUL)
    fun ldiv() = insn(LDIV)
    fun lrem() = insn(LREM)
    fun lneg() = insn(LNEG)
    fun lshl() = insn(LSHL)
    fun lshr() = insn(LSHR)
    fun lushr() = insn(LUSHR)
    fun lor() = insn(LOR)
    fun land() = insn(LAND)
    fun lxor() = insn(LXOR)

    fun fadd() = insn(FADD)
    fun fsub() = insn(FSUB)
    fun fmul() = insn(FMUL)
    fun fdiv() = insn(FDIV)
    fun frem() = insn(FREM)
    fun fneg() = insn(FNEG)

    fun dadd() = insn(DADD)
    fun dsub() = insn(DSUB)
    fun dmul() = insn(DMUL)
    fun ddiv() = insn(DDIV)
    fun drem() = insn(DREM)
    fun dneg() = insn(DNEG)

    fun i2l() = insn(I2L)
    fun i2f() = insn(I2F)
    fun i2d() = insn(I2D)
    fun i2b() = insn(I2B)
    fun i2c() = insn(I2C)
    fun i2s() = insn(I2S)
    fun l2i() = insn(L2I)
    fun l2f() = insn(L2F)
    fun l2d() = insn(L2D)
    fun f2i() = insn(F2I)
    fun f2l() = insn(F2L)
    fun f2d() = insn(F2D)
    fun d2i() = insn(D2I)
    fun d2l() = insn(D2L)
    fun d2f() = insn(D2F)

    fun lcmp() = insn(LCMP)
    fun fcmpl() = insn(FCMPL)
    fun fcmpg() = insn(FCMPG)
    fun dcmpl() = insn(DCMPL)
    fun dcmpg() = insn(DCMPG)


    fun goto(label: LabelNode) = +JumpInsnNode(GOTO, label)
    fun jsr(label: LabelNode) = +JumpInsnNode(JSR, label)

    fun ifeq(label: LabelNode) = +JumpInsnNode(IFEQ, label)
    fun ifne(label: LabelNode) = +JumpInsnNode(IFNE, label)
    fun iflt(label: LabelNode) = +JumpInsnNode(IFLT, label)
    fun ifle(label: LabelNode) = +JumpInsnNode(IFLE, label)
    fun ifge(label: LabelNode) = +JumpInsnNode(IFGE, label)
    fun ifgt(label: LabelNode) = +JumpInsnNode(IFGT, label)

    fun if_icmplt(label: LabelNode) = +JumpInsnNode(IF_ICMPLT, label)
    fun if_icmple(label: LabelNode) = +JumpInsnNode(IF_ICMPLE, label)
    fun if_icmpge(label: LabelNode) = +JumpInsnNode(IF_ICMPGE, label)
    fun if_icmpgt(label: LabelNode) = +JumpInsnNode(IF_ICMPGT, label)
    fun if_icmpeq(label: LabelNode) = +JumpInsnNode(IF_ICMPEQ, label)
    fun if_icmpne(label: LabelNode) = +JumpInsnNode(IF_ICMPNE, label)
    fun if_acmpeq(label: LabelNode) = +JumpInsnNode(IF_ACMPEQ, label)

    fun ifnull(label: LabelNode) = +JumpInsnNode(IFNULL, label)
    fun ifnonnull(label: LabelNode) = +JumpInsnNode(IFNONNULL, label)

    fun lookupswitch(defaultLabel: LabelNode, lookup: Pair<IntArray, Array<LabelNode>>) =
        +LookupSwitchInsnNode(defaultLabel, lookup.first, lookup.second)

    fun getstatic(owner: String, name: String, desc: String) = +FieldInsnNode(GETSTATIC, owner, name, desc)
    fun putstatic(owner: String, name: String, desc: String) = +FieldInsnNode(PUTSTATIC, owner, name, desc)
    fun getfield(owner: String, name: String, desc: String) = +FieldInsnNode(GETFIELD, owner, name, desc)
    fun putfield(owner: String, name: String, desc: String) = +FieldInsnNode(PUTFIELD, owner, name, desc)

    fun invokevirtual(owner: String, name: String, desc: String, `interface`: Boolean = false) =
        +MethodInsnNode(INVOKEVIRTUAL, owner, name, desc, `interface`)

    fun invokespecial(owner: String, name: String, desc: String, `interface`: Boolean = false) =
        +MethodInsnNode(INVOKESPECIAL, owner, name, desc, `interface`)

    fun invokestatic(owner: String, name: String, desc: String, `interface`: Boolean = false) =
        +MethodInsnNode(INVOKESTATIC, owner, name, desc, `interface`)

    fun invokeinterface(owner: String, name: String, desc: String, `interface`: Boolean = false) =
        +MethodInsnNode(INVOKEINTERFACE, owner, name, desc, `interface`)

    fun new(type: String) = +TypeInsnNode(NEW, type)
    fun newarray(type: Int) = +IntInsnNode(NEWARRAY, type)
    fun anewarray(desc: String) = +TypeInsnNode(ANEWARRAY, desc)
    fun newboolarray() = newarray(T_BOOLEAN)
    fun newchararray() = newarray(T_CHAR)
    fun newbytearray() = newarray(T_BYTE)
    fun newshortarray() = newarray(T_SHORT)
    fun newintarray() = newarray(T_INT)
    fun newlongarray() = newarray(T_LONG)
    fun newfloatarray() = newarray(T_FLOAT)
    fun newdoublearray() = newarray(T_DOUBLE)

    fun arraylength() = insn(ARRAYLENGTH)

    fun athrow() = insn(ATHROW)

    fun checkcast(descriptor: String) = +TypeInsnNode(CHECKCAST, descriptor)
    fun instanceof(descriptor: String) = +TypeInsnNode(INSTANCEOF, descriptor)

    fun ireturn() = insn(IRETURN)
    fun lreturn() = insn(LRETURN)
    fun freturn() = insn(FRETURN)
    fun dreturn() = insn(DRETURN)
    fun areturn() = insn(ARETURN)
    fun _return() = insn(RETURN)

    fun frame(type: Int, numLocal: Int, local: Array<Any>?, numStack: Int, stack: Array<Any>?) =
        +FrameNode(type, numLocal, local, numStack, stack)

    fun getIntInsn(value: Int) =
        when (value) {
            in -1..5 -> InsnNode(value + 3)
            in Byte.MIN_VALUE..Byte.MAX_VALUE -> IntInsnNode(BIPUSH, value)
            in Short.MIN_VALUE..Short.MAX_VALUE -> IntInsnNode(SIPUSH, value)
            else -> LdcInsnNode(value)
        }

    fun getLongInsn(value: Long) =
        when (value) {
            in 0..1 -> InsnNode((value + 9).toInt())
            else -> LdcInsnNode(value)
        }

    fun getFloatInsn(value: Float) =
        when {
            value % 1 == 0f && value in 0f..2f -> InsnNode((value + 11).toInt())
            else -> LdcInsnNode(value)
        }

    fun getDoubleInsn(value: Double) =
        when {
            value % 1 == 0.0 && value in 0.0..1.0 -> InsnNode((value + 14).toInt())
            else -> LdcInsnNode(value)
        }
}

fun insnBuilder(builder: InsnBuilder.() -> Unit) = InsnBuilder().also(builder).insnList