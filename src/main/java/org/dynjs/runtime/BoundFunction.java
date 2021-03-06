package org.dynjs.runtime;

import java.util.Arrays;

public class BoundFunction extends AbstractFunction {
    
    private static String[] figureBoundParameters(JSFunction fn, Object[] boundArgs) {
        String[] formalParams = fn.getFormalParameters();
        int numRemainingArgs = formalParams.length - boundArgs.length;
        
        if ( numRemainingArgs <= 0 ) {
            return new String[0];
        }
        
        String[] remainingParams = new String[numRemainingArgs];
        
        for ( int i = 0 ; i < numRemainingArgs ; ++i ) {
            remainingParams[i] = formalParams[ i + boundArgs.length ];
        }
        
        return remainingParams;
    }

    private JSFunction target;
    private Object boundThis;
    private Object[] boundArgs;

    public BoundFunction(LexicalEnvironment scope, final JSFunction target, final Object boundThis, final Object[] boundArgs) {
        super(scope, true, figureBoundParameters(target, boundArgs));
        this.target = target;
        this.boundThis = boundThis;
        this.boundArgs = boundArgs;
    }
    
    @Override
    public Object call(ExecutionContext context) {

        Arguments argsObj = (Arguments) context.resolve("arguments").getValue(context);
        int numExtraArgs = (int) argsObj.get(context, "length");
        
        Object[] args = new Object[ this.boundArgs.length + numExtraArgs ];
        
        for ( int i = 0 ; i < this.boundArgs.length ; ++i ) {
            args[i] = this.boundArgs[i];
        }
        
        for ( int i = 0 ; i < numExtraArgs ; ++i ) {
            Object v = argsObj.get( context, "" + i );
            if ( v instanceof Reference ) {
                v = ((Reference) v).getValue(context);
            }
            args[i + this.boundArgs.length ] = v;
        }
        
        return context.call(this.target, this.boundThis, args);
    }


    /*
    @Override
    public Object call(ExecutionContext context, Object self, Object... args) {
        // 15.3.4.5.1
        System.err.println( "called args: " + args.length + " // " + Arrays.asList( args ) );
        Object[] allArgs = new Object[boundArgs.length + args.length];

        for (int i = 0; i < boundArgs.length; ++i) {
            allArgs[i] = boundArgs[i];
        }

        for (int i = boundArgs.length, j = 0; i < boundArgs.length + args.length; ++i, ++j) {
            allArgs[i] = args[j];
        }

        if (self != Types.UNDEFINED) {
            return context.call(this.target, self, allArgs);
        } else {
            return context.call(this.target, this.boundThis, allArgs);
        }
    }
    */

    @Override
    public boolean hasInstance(ExecutionContext context, Object v) {
        // 15.3.4.5.3
        return target.hasInstance(context, v);
    }

    @Override
    public JSObject createNewObject(ExecutionContext context) {
        return target.createNewObject(context);
    }

    @Override
    public String getFileName() {
        return target.getFileName();
    }

    @Override
    public String getDebugContext() {
        return "bound-function:" + target.getDebugContext();
    }

}
