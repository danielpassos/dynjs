package org.dynjs.compiler.partial;

import me.qmx.jitescript.JiteClass;

import org.dynjs.Config;
import org.dynjs.codegen.CodeGeneratingVisitorFactory;
import org.dynjs.compiler.AbstractCompiler;
import org.dynjs.runtime.DynamicClassLoader;

public abstract class AbstractPartialCompiler extends AbstractCompiler implements PartialCompiler {
    
    private CodeGeneratingVisitorFactory factory;
    private DynamicClassLoader classLoader;

    public AbstractPartialCompiler(Config config, DynamicClassLoader classLoader, CodeGeneratingVisitorFactory factory) {
        super( config, factory );
        this.classLoader = classLoader;
    }
    
    public AbstractPartialCompiler(AbstractPartialCompiler parent) {
        super( parent );
        this.classLoader = parent.classLoader;
    }
    
    public DynamicClassLoader getClassLoader() {
        return this.classLoader;
    }
    
    
    protected <T> Class<T> defineClass(JiteClass cls) {
        return defineClass( this.classLoader, cls );
    }
    

}
