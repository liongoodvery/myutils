package org.lion.utils;

import java.lang.instrument.*;
import java.security.ProtectionDomain;

public class GetBytecode implements ClassFileTransformer {
    private static Instrumentation inst;

    public static synchronized void agentmain(String args, Instrumentation inst) {
        GetBytecode.inst = inst;
    }

    public static synchronized void premain(String args, Instrumentation inst) {
        GetBytecode.inst = inst;
    }

    public static synchronized byte[] getClassFile(Class cls) throws UnmodifiableClassException {
        Instrumentation inst = GetBytecode.inst;
        if (inst == null) {
            throw new IllegalStateException("Agent has not been loaded");
        }

        GetBytecode transformer = new GetBytecode();
        inst.addTransformer(transformer, true);
        inst.retransformClasses(cls);
        inst.removeTransformer(transformer);
        return transformer.classFile;
    }

    private byte[] classFile;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain pd, byte[] classFile) throws IllegalClassFormatException {
        if (classBeingRedefined != null) {
            this.classFile = classFile;
        }
        return null;
    }
}